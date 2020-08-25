package Board;

import java.util.ArrayList;
import java.util.Scanner;

public class App {

	ArticleDao dao = new ArticleDao();
	MemberManage mm = new MemberManage();
	
	public void start() {
		Scanner sc = new Scanner(System.in);
		ArticleDao dao = new ArticleDao();
		String title;
		String body;
		String Id;
		String Pw;
		String name;
		print p = new print();
		menu m = new menu();
		Article loginedmember = null;
		login l = new login();
		
		while(true) {
			System.out.println("");
			p.menu();
			
			String s = sc.nextLine();
			
			if(s.equals("add")) {
				
				if(loginedmember == null) {
					System.out.println("로그인이 필요합니다.");
				}
				
				else {
					System.out.println("");
					System.out.println("제목을 입력하십시오.");
					title = sc.nextLine();
					System.out.println("내용을 입력하십시오.");
					body = sc.nextLine();

					Article article = new Article();
					article.setTitle(title);
					article.setBody(body);
					article.setWriter(loginedmember.getMember());
					
					dao.addData(article);
				}
			}
			
			else if(s.equals("signup")) {
				System.out.println("");
				System.out.println("아이디를 입력해주십시오.");
				Id = sc.nextLine();
				System.out.println("비밀번호를 입력해주십시오.");
				Pw = sc.nextLine();
				System.out.println("이름을 입력해주십시오.");
				name = sc.nextLine();
				System.out.println("가입이 완료되었습니다.");
				
				Article member = new Article();
				member.setMember(Id);
				member.setPassword(Pw);
				member.setName(name);
				
				mm.signup(member);
			}
			
			else if(s.equals("login")) {

				System.out.print("아이디 : ");
				String id = sc.nextLine();
				System.out.print("비밀번호 : ");
				String pw = sc.nextLine();

				l.Login(id, pw);
			}
			
			else if(s.equals("list")) {
				
				m.print();
			}
			
			else if(s.equals("read")) {
				ArrayList<Article> articles = dao.listData(); 
				
				for(int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					
					System.out.println("");
					System.out.println("번호 : " + article.getId());
		    		System.out.println("제목 : " + article.getTitle());
		    		System.out.println("");
				}
			}
			
			else if(s.equals("detail")) {
				System.out.println("");
				System.out.println("보고싶은 게시물의 번호를 입력해주십시오.");
				int id = sc.nextInt();
				sc.nextLine();
				
				Article article = dao.getArticleById(id);
				
				if(article == null) { 
					System.out.println("");
					System.out.println("게시물이 존재하지 않습니다.");
				}
				else {
					m.print();
				}
			}
			
			else if(s.equals("update")) {
				System.out.println("");
				System.out.println("수정을 원하는 게시물의 번호를 입력해주십시오.");
				int id = sc.nextInt();
				sc.nextLine();
				
				Article article = dao.getArticleById(id);
				
				if(article == null) { 
					System.out.println("");
					System.out.println("게시물이 존재하지 않습니다.");
				}
				else {
					System.out.println("");
					System.out.println("새로운 제목을 입력해주십시오.");
					title = sc.nextLine();
					article.setTitle(title);
					System.out.println("새로운 내용을 입력해주십시오.");
					body = sc.nextLine();
					article.setBody(body);
					
					dao.updateData(id, article);

					m.print();
				}
			}
			else if(s.equals("delete")) {
				System.out.println("");
				System.out.println("삭제를 원하는 게시물의 번호를 입력해주십시오.");
				int id = sc.nextInt();
				sc.nextLine();
				
				Article article = dao.getArticleById(id);
				
				if(article == null) { 
					System.out.println("");
					System.out.println("게시물이 존재하지 않습니다.");
				}
				else {
					dao.deleteData(article);

					m.print();
				}
			}	
			else if(s.equals("search")) {
				System.out.println("검색을 원하는 문자를 입력해주십시오. ");
				String word = sc.nextLine();
				
				ArrayList<Article> articles = dao.listData();
				
				for(int i = 0; i < articles.size(); i++) {
					Article article = articles.get(i);
					if(article.getTitle().contains(word)) {
						System.out.println("번호 : " + article.getId());
						System.out.println("제목 : " + article.getTitle());
					}
				}
			}
			else if(s.equals("exit")) {
				System.out.println("프로그램을 종료합니다.");
				break;
			}
		}
	}
}

class login {
	

	ArrayList<Article> members = new ArrayList<>();
	
	void Login(String id, String pw) {

		if(LoginManage(id, pw) == -1) {
			for(int i = 0; i < members.size(); i++) {
				Article member = members.get(i);
				if(member.getMember().equals(id)) {
					if(member.getPassword().equals(pw)) {
						System.out.println(member.getName() + "님! 환영합니다.");
					}
				}
			}
		}
		
		else if(LoginManage(id, pw) == 0) {
			System.out.println("틀린 비밀번호입니다.");
		}
		
		else if(LoginManage(id, pw) == 1) {
			System.out.println("틀린 아이디입니다.");
		}
	}
	
	int LoginManage(String id, String pw) {

		for(int i = 0; i < members.size(); i++) {
			Article member = members.get(i);
			if(member.getMember().equals(id)) {
				if(member.getPassword().equals(pw)) {
					return -1;
				}
				else {
					return 0;
				}
			}
			else {
				return 1;
			}
		}
		
		return 10;
	}
}

class print {
	void menu() {
		System.out.println("원하는 기능에 맞게 입력해주십시오.");
		System.out.println("게시물 추가(로그인 후 이용가능) : add");
		System.out.println("게시물 조회 : list");
		System.out.println("게시물 목록 조회 : read");
		System.out.println("게시물 상세보기 : detail");
		System.out.println("게시물 수정 : update");
		System.out.println("게시물 삭제 : delete");
		System.out.println("게시물 검색 : search");
		System.out.println("프로그램 종료 : exit");
	}
}

class menu {
	ArticleDao dao = new ArticleDao();
	void print() {
		ArrayList<Article> articles = dao.listData();
		
		for(int i = 0; i < articles.size(); i++) {
			Article article = articles.get(i);
			
			System.out.println("번호 : " + article.getId());
			System.out.println("제목 : " + article.getTitle());
			System.out.println("내용 : " + article.getBody());
			System.out.println("작성자 : " + article.getWriter());
			System.out.println("");
		}
	}
}