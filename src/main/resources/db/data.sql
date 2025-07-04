INSERT INTO user_tb (username, password, email, address, created_at) VALUES
('ssar', '1234', 'ssar@nate.com', '부산시 진구 중앙대로', NOW()),
('cos', '1234', 'cos@gmail.com', '서울시 강남구 테헤란로', NOW()),
('love', '5678', 'love@example.com', '인천시 부평구 부평대로', NOW()),
('guest123', 'guestpass', 'guest123@yahoo.co.kr', '대전시 유성구 대학로', NOW()),
('admin', 'admin123', 'admin@mysite.com', '광주시 서구 상무대로', NOW());

INSERT INTO company_tb (company_name, password, address, business_registration_number, email, created_at) VALUES
('네이버', '1234', '경기도 성남시 분당구 불정로 61', '129-86-31394', 'naver@naver.com', NOW()),
('카카오', '1234', '제주특별자치도 제주시 첨단로 242', '120-81-47521', 'kakao@kakao.com', NOW()),
('라인', '1234', '경기도 성남시 분당구 황새울로240번길', '129-87-00203', 'line@lineplus.com', NOW()),
('쿠팡', '1234', '서울특별시 송파구 송파대로 570', '120-88-00767', 'coupang@coupang.com', NOW()),
('우아한형제들', '1234', '서울특별시 송파구 위례성대로 2', '120-87-65763', 'woowabros@woowahan.com', NOW());

INSERT INTO announce_tb (company_id, title, content, work_location, start_date, end_date, interest_count) VALUES
(1, '[네이버] 2025년 신입 백엔드 서버 개발자 채용', '네이버의 다양한 서비스들을 함께 만들어나갈 열정적인 신입 개발자를 모집합니다. 최고의 동료들과 함께 성장할 기회를 잡으세요.', '경기도 성남시', NOW(), '2025-07-31', 0),
(2, '[카카오] 프론트엔드 개발자 경력직 채용 (3년 이상)', '카카오톡, 다음 등 국민 서비스를 만들어나갈 프론트엔드 개발자를 찾습니다. JavaScript에 대한 깊은 이해가 있는 분을 우대합니다.', '제주특별자치도 제주시', NOW(), '2025-08-15', 0),
(3, '[라인] 글로벌 서비스 기획자 모집', '전 세계 수억 명이 사용하는 라인 메신저의 새로운 기능을 기획하고 개선할 인재를 찾습니다. 글로벌 마인드와 커뮤니케이션 능력이 중요합니다.', '경기도 성남시', NOW(), '2025-07-20', 0),
(4, '[쿠팡] 데이터 분석가 인턴십', '쿠팡의 방대한 데이터를 분석하여 물류, 커머스 혁신을 이끌어갈 데이터 분석가 인턴을 모집합니다. 통계적 지식과 분석 툴 활용 능력이 필요합니다.', '서울특별시 송파구', NOW(), '2025-07-10', 0),
(5, '[배달의민족] DevOps 엔지니어 채용', '배달의민족 서비스의 안정적인 인프라를 책임질 DevOps 엔지니어를 모집합니다. 클라우드 환경과 자동화에 대한 경험이 있는 분을 환영합니다.', '서울특별시 송파구', NOW(), '2025-08-01', 0);

INSERT INTO resume_tb (user_id, title, portfolio_url, self_introduction, is_public, created_at) VALUES
(1, '성실하게 성장하는 백엔드 개발자, 김철수입니다.', 'https://github.com/kimcheolsu', '항상 배우는 자세로 빠르게 기술을 습득하고 팀에 기여하고 싶습니다. Java와 Spring에 자신 있습니다.', TRUE, NOW()),
(2, '사용자 경험을 중요시하는 프론트엔드 개발자 박영희', 'https://younghee.dev', 'React와 Vue.js를 사용하여 직관적이고 아름다운 UI/UX를 만드는 것을 좋아합니다.', TRUE, NOW()),
(3, '데이터로 말하는 데이터 분석가 이민준입니다.', 'https://linkedin.com/in/minjun', 'SQL과 Python을 활용하여 비즈니스 인사이트를 도출하고 문제 해결에 기여한 경험이 있습니다.', FALSE, NOW()),
(4, '꼼꼼하고 창의적인 신입 기획자 최은하', 'https://notion.so/eunha-plan', '시장 조사와 사용자 분석을 통해 새로운 아이디어를 구체화하고 프로젝트를 성공으로 이끄는 기획자가 되고 싶습니다.', TRUE, NOW()),
(5, '팀과 함께 성장하는 클라우드 엔지니어 정수빈', 'https://velog.io/@subin-jung', 'AWS와 Kubernetes 환경에서 안정적인 인프라를 구축하고 운영하는 데 관심이 많습니다.', FALSE, NOW());
--
--INSERT INTO user_bookmark_tb (user_id, announce_id) VALUES
--(1, 2),
--(1, 5),
--(2, 1),
--(3, 4),
--(4, 3);
--
---- 5. 회사 입장 이력서 관리 (check_resume_tb) 샘플 데이터
--INSERT INTO check_resume_tb (resume_id, company_id, created_at) VALUES
--(1, 2, NOW()),
--(2, 1, NOW()),
--(3, 4, NOW()),
--(4, 3, NOW()),
--(1, 5, NOW());

INSERT INTO skill_tag_tb (skill_tag_name, skill_tag_no) VALUES
('Java', '001'),
('Spring', '002'),
('React', '003'),
('Vue.js', '004'),
('SQL', '005'),
('Python', '006'),
('기획', '007'),
('마케팅', '008'),
('AWS', '009'),
('Kubernetes', '010'),
('Docker', '011'),
('JavaScript', '012'),
('HTML/CSS', '013'),
('Node.js', '014'),
('데이터분석', '015'),
('머신러닝', '016'),
('클라우드', '017'),
('DevOps', '018'),
('백엔드', '019'),
('프론트엔드', '020');

INSERT INTO resume_skill_tag_tb (resume_id, skill_tag_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(2, 4),
(3, 5),
(3, 6),
(4, 7),
(4, 8),
(5, 9),
(5, 10);