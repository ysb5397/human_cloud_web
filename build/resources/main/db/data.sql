-- 유저 샘플 데이터 (5명의 사용자)
INSERT INTO user_tb (username, password, email, address, created_at) VALUES
('ssar', '1234', 'ssar@nate.com', '부산시 진구 중앙대로', NOW()),
('cos', '1234', 'cos@gmail.com', '서울시 강남구 테헤란로', NOW()),
('love', '5678', 'love@example.com', '인천시 부평구 부평대로', NOW()),
('guest123', 'guestpass', 'guest123@yahoo.co.kr', '대전시 유성구 대학로', NOW()),
('admin', 'admin123', 'admin@mysite.com', '광주시 서구 상무대로', NOW());