DROP TABLE users;

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),                  -- ユーザーID (自動インクリメント)
    last_name VARCHAR(20) NOT NULL,         -- 姓 (最大50文字)
    first_name VARCHAR(20) NOT NULL,        -- 名 (最大50文字)
    password text,         -- パスワード (暗号化前提で最大255文字)
    email VARCHAR(255) UNIQUE NOT NULL,     -- メールアドレス (最大255文字、ユニーク)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 作成日時 (デフォルト現在時刻)
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- 更新日時 (デフォルト現在時刻)
);

INSERT INTO users (last_name, first_name, email, password) VALUES ('佐々木', '崚', 'sasaryo0301@gmail.com', '');
INSERT INTO users (last_name, first_name, email, password) VALUES ('高橋', '孝輔', 'takakou0402@gmail.com', '');

