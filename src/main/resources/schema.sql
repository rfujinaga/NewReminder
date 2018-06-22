CREATE TABLE service (id INT auto_increment,
	s_name CHAR(40) NOT NULL,
	r_date DATE NOT NULL,
	t_period DATE NOT NULL,	
	mailaddress CHAR(200),
	card_bra CHAR(40),
	card_num CHAR(16),
	s_id CHAR(40),
	password CHAR(40),
	other CHAR(400));
	
INSERT INTO service VALUES ('0',
	'DAZN',
	'2018-4-28',
	'2018-5-28',					/*テストデータ*/
	'DAZN＠gmail.com',
	'VISA',
	'0000123456780000',
	'rfujinaga',
	'this is password',
	'追加でメモしておきたいことを入力');

INSERT INTO service VALUES ('1',
	'Hulu',
	'2018-6-14',
	'2018-6-28',
	'Hulu＠gmail.com',
	'銀聯',
	'0000123456780000',
	'rfujinaga',
	'thisispassword',
	'追加でメモしておきたいことを入力');

INSERT INTO service VALUES ('2',
	'U-NEXT',
	'2018-6-1',
	'2018-6-23',					 /*テストデータ*/
	'UNEXT＠gmail.com',
	'Master Card',
	'0000123456780000',
	'rfujinaga',
	'thisispassword',
	'追加でメモしておきたいことを入力');

INSERT INTO service VALUES ('3',
	'Netflix',
	'2018-6-5',
	'2018-7-5',
	'flix＠gmail.com',
	'JCB',
	'0000123456780000',
	'rfujinaga',
	'thisispassword',
	'追加でメモしておきたいことを入力');
	
INSERT INTO service VALUES ('4',
	'Amazon prime',
	'2018-6-28',
	'2018-6-22',						/*テストデータ*/
	'Amazon＠gmail.com',
	'American Express',
	'0000123456780000',
	'rfujinaga',
	'thisispassword',
	'追加でメモしておきたいことを入力');
	