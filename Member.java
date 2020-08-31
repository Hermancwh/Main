import java.util.ArrayList;
import java.util.Scanner;

public class Member {
    private String memberId;
    private String name;
    private String phoneNum;
    private int daysVisit;
    private String dateJoined;
    private String expireDate;
    protected static ArrayList<Member> memberList;

    public Member() {
    }

    public Member(String memberId, String name, String phoneNum, int daysVisit, String dateJoined, String expireDate) {
        this.memberId = memberId;
        this.name = name;
        this.phoneNum = phoneNum;
        this.daysVisit = daysVisit;
        this.dateJoined = dateJoined;
        this.expireDate = expireDate;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getDaysVisit() {
        return daysVisit;
    }

    public void setDaysVisit(int daysVisit) {
        this.daysVisit = daysVisit;
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }
    
    
    public static void initial(){
        memberList = new ArrayList<>();
        memberList.add(new Member("MC001", "Habo", "0163658975", 1, "19/03/2020", "19/09/2020"));
        memberList.add(new Member("MC002", "Macd", "0122252614", 2, "26/05/2020", "26/11/2020"));
        memberList.add(new Member("MC003", "Labin", "0162528612", 4, "15/06/2020", "15/12/2020"));
    }

    public static ArrayList<Member> getMemberList() {
        return memberList;
    }

    public static void setMemberList(ArrayList<Member> memberList) {
        Member.memberList = memberList;
    }
    
    public static boolean isMember(String theID){
        initial();  
        int checkID = 0;
        for(int i = 0; i < memberList.size(); i++){
            if(memberList.get(i).getMemberId().equalsIgnoreCase(theID)){
                checkID = 1;
                return true;
            }
        }    
        if(checkID == 0){
            System.out.println("The member ID entered doesn't exist!!!");
        }
        return false;
    }
    
    public static int memberVisitNum(String proveID){
        initial();
        for(int i = 0; i < memberList.size(); i++){
            if(memberList.get(i).getMemberId().equalsIgnoreCase(proveID)){
                return memberList.get(i).getDaysVisit();
            }
        }
        return 0;
    }
    
}
