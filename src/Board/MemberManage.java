package Board;
 
import java.util.ArrayList;

public class MemberManage {
	ArrayList<Article> members = new ArrayList<>();
	
	void signup(Article member) {
		members.add(member);
	}
	
	ArrayList<Article> memberData() {
		return members;
	}
	
	int getIndexById(String id) {
		for(int i = 0; i < members.size(); i++) {
			if(id.equals(members.get(i).getId())) {
				return i;
			}
		}
		
		return -1;
	}
	
	Article getMemberById(String id) {
		int index = getIndexById(id);
		
		if(index == -1) {
			return null;
		}
		
		return members.get(index);
		
	}
}