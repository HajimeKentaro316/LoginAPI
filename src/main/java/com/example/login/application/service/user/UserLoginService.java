package com.example.login.application.service.user;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.login.application.dto.login.LoginRequestData;
import com.example.login.application.dto.login.LoginResponseData;
import com.example.login.application.dto.user.RegisterUserRequestData;
import com.example.login.application.dto.user.RegisterUserResponseData;
import com.example.login.domain.model.user.User;
import com.example.login.domain.model.user.UserName;
import com.example.login.infrastructure.repository.user.IUserRepository;

@Service
public class UserLoginService {

    @Autowired
    private Logger logger;

    private final BCryptPasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;

    // コンストラクタでBCryptPasswordEncoderとIUserRepositoryを注入
    @Autowired
    public UserLoginService(@Lazy BCryptPasswordEncoder passwordEncoder, IUserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    // ログイン用
    public LoginResponseData login(LoginRequestData loginRequestData) {
        String loginEmail = loginRequestData.getEmail();
        String loginPassword = loginRequestData.getPassword();

        Optional<User> user = userRepository.findByEmail(loginEmail);
        if (user.isPresent()) {
            String storedHashedPassword = user.get().getPassword();
            if (passwordEncoder.matches(loginPassword, storedHashedPassword)) {
                LoginResponseData loginResponseData = new LoginResponseData(
                    user.get(),
                    true,
                    null
                );
                logger.debug("一致するユーザーが見つかりました。");
                return loginResponseData;
            } else {
                logger.error("パスワードが一致していません");
                return errorLoginResponse();
            }
        } else {
            logger.error("メールアドレスが不正です");
            return errorLoginResponse();
        }
    }

    public LoginResponseData errorLoginResponse() {
        return new LoginResponseData(null, false, "該当するユーザーが見つかりません");
    }

    // 会員登録用
    public RegisterUserResponseData registerUser(RegisterUserRequestData requestUser) {
        // Userインスタンスを生成
        UserName newUserName = new UserName(requestUser.getFirstName(), requestUser.getLastName());
        User newUser = new User(newUserName, requestUser.getEmail(), requestUser.getPassword());

        // 本当はUserのequalsメソッドを使ってドメインオブジェクトの同一性の比較を行う
        Optional<User> oldUser = userRepository.findByEmail(newUser.getEmail());
        if (oldUser.isPresent()) {
            logger.error("このメールアドレスは登録済です");
            return new RegisterUserResponseData(
                false,
                "このメールアドレスは登録済です"
            );
        }

        // パスワードの暗号化
        try {
            String encodedPassword = passwordEncoder.encode(newUser.getPassword());
            newUser.setEncodePassword(encodedPassword);
            userRepository.save(newUser);

            return new RegisterUserResponseData(
                true,
                "登録に成功しました"
            );
        } catch (Exception e) {
            logger.error("登録に失敗しました");
            return new RegisterUserResponseData(
                false,
                "登録に失敗しました"
            );
        }
    }
}
