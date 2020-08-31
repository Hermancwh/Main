import java.util.Scanner;
import java.util.Scanner;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Bills{
    static int count = 0;
    public static void Billing(tables[] table,countSummary[] summarying)
    {
        Scanner scan = new Scanner(System.in);
        Product.initProd();
        ArrayList<Product> ProductList = Product.getProductList();
        ArrayList<Member> MemberList = Member.getMemberList();
        if(table[0] == null){
                for(int i = 0; i < 15; i++){
                    table[i] = new tables(i+1, "Empty", 0, 0, 0, 0, '-');
                }
            }
        
        //Show all the tables
            System.out.println("\nTable Details");
            System.out.println("=============");
            System.out.println("No " + "||" + "  Condition " + "||" + " Total Head " + "||" + " Adult " + "||" + " Children " + "||" + " Elder " + "||" + " Combo Set " + "||");
            for(int i = 0; i <15; i++){
                System.out.printf("%2d || %10s || %10d || %5d || %8d || %5d || %9c ||\n" ,table[i].getTableNo(), table[i].getOccupy(), table[i].getPersonCount(),
                        table[i].getAdultCount() , table[i].getChildCount(), table[i].getElderCount(), table[i].getComboSet());
            }
            
        //Enter table number   
        int scanTableNumber = 0;
        char confirm;
        boolean no, notEmpty, quitBill;
        String checkEmpty;        
        do{
            do{
                System.out.print("Select table No which need to make billing (1-15):");
                try
                {            
                    scanTableNumber = scan.nextInt();     
                    no = true;   
                    scan.nextLine();
                }
                catch (Exception ex){
                    System.out.print("Invalid table No input");
                    no = false;
                    scan.nextLine();
                }    
                if(scanTableNumber <=0 || scanTableNumber > 15){
                    System.out.println("The value must in this range 1-15...");
                }
            }while(!no || scanTableNumber <= 0 || scanTableNumber > 15);
            
            checkEmpty = table[scanTableNumber -1].getOccupy();
            if(!checkEmpty.equals("Empty")){
                notEmpty = true;
                quitBill = false;
            }else{
                notEmpty = false;
                System.out.println("This table is empty!");
                System.out.println("You can't proceed to bill");
                //ask continue or quit
                do{
                    System.out.print("Would like to continue the bill or exit (Y/N): ");
                    confirm = scan.next().charAt(0);   
                    scan.nextLine();
                    confirm = Character.toUpperCase(confirm);                
                    if(!(confirm == 'Y' || confirm == 'N')){
                        System.out.println("Just Enter Y to continue the bill and N to not.");
                    }
                }while(!(confirm == 'Y' || confirm == 'N'));
                if(confirm == 'Y'){
                    quitBill = false;
                }else{
                    quitBill = true;
                    break;
                }

            }               
        }while(!notEmpty);
        Member askMember = new Member();
        boolean member;
        int dayVisit = 0;
        char confirmMember, continueMember;
        //Start billing'
        if(!quitBill){
            char comboSet;
            comboSet = table[scanTableNumber-1].getComboSet();
            // got member > 3 days 
            // got member !>3days
            // no member 
            // got 3 methods of calculation
            do{
                System.out.print("Are you a Member? (Y/N)>");
                confirmMember = scan.next().charAt(0);
                scan.nextLine();
                confirmMember = Character.toUpperCase(confirmMember);
                if(!(confirmMember == 'Y' || confirmMember == 'N')){
                    System.out.println("Please 'Y' or 'N' to continue");
                }
            }while(!(confirmMember == 'Y' || confirmMember == 'N'));
            //member validation
            if(confirmMember == 'Y'){
                do{
                    System.out.print("Enter Member ID : "); 
                    String proveID = scan.nextLine();
                    member = askMember.isMember(proveID);
                    continueMember = 'N';
                    if(!member){
                        System.out.print("Invalid member ID!!!");
                        do{
                            System.out.print("Do you want to enter again? (Y/N)>");
                            continueMember = scan.next().charAt(0);
                            scan.nextLine();
                            continueMember = Character.toUpperCase(continueMember);
                            if(!(continueMember == 'Y' || continueMember == 'N')){
                                System.out.println("Please 'Y' or 'N' to continue");
                            }
                        }while(!(continueMember == 'Y' || continueMember == 'N'));
                    }else{
                        dayVisit = askMember.memberVisitNum(proveID);
                    }
                }while(continueMember == 'Y');             
            }else{
                member = false;
            }    
            
            switch(comboSet){
                case 'A': comboA(table,ProductList,MemberList,scanTableNumber,summarying, member,dayVisit);break;
                case 'B': comboB(table,ProductList,MemberList,scanTableNumber,summarying, member,dayVisit);break;
                case 'C': comboC(table,ProductList,MemberList,scanTableNumber,summarying, member,dayVisit);break;
                case 'D': comboD(table,ProductList,MemberList,scanTableNumber,summarying, member,dayVisit);break;
            }   
        }
    }
    public static void displaywhatever(tables[] table,ArrayList<Product> ProductList,int countAdult,
                                        int countKid, int countElder,double subTotal, double grandTotal, 
                                        double SST,double totalAdult, double totalKid,double totalElder,countSummary[] summarying, int scanTableNumber, boolean member, int dayVisit){
        
        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = todayDate.format(formatDate);
        System.out.printf(date);
        System.out.printf("\t\tTable No :%d\n", scanTableNumber); 
        System.out.printf("Set Name:%c\n", table[scanTableNumber].getComboSet()); //Read what kind set first 
        System.out.printf("Category\tNumber of pax\tPrice per person\tTotal\n");
        System.out.printf("========\t=============\t================\t======\n");
        //Display
        if(countAdult !=0){
            double PricePerAdult = totalAdult / countAdult;
            System.out.printf("Adult\t%23d\t%-7.2f\t%7.2f\n",countAdult,PricePerAdult,totalAdult);
        }
        if(countKid !=0){
            double PricePerKid = totalKid / countKid;
            System.out.printf("Kid\t%23d\t%-7.2f\t%7.2f\n",countKid,PricePerKid,totalKid);
        }
        if(countElder !=0){
            double PricePerElder = totalElder / countAdult;
            System.out.printf("Elder\t%23d\t%-7.2f\t%7.2f\n",countElder,PricePerElder,totalElder);
        }
        System.out.printf("Subtotal :5.2f\n",subTotal);
        
        if(member){
            System.out.printf("Discount given : 5%%\n");
        }else{
            System.out.printf("Discount given : 0%%\n");
        }
        System.out.printf("SST :%-5.2f\n",SST);
        System.out.printf("--------------------\n");
        System.out.printf("Total :%-5.2f\n",grandTotal); //One is member, One is without member 
        System.out.printf("--------------------\n");

}
    
    
    public static void comboA(tables[] table,ArrayList<Product> ProductList,ArrayList<Member> MemberList,int scanTableNumber,countSummary [] summarying, boolean member, int dayVisit){
        int adult, kid, elder;
        double countAdult, countKid,  countElder;
        double SST = 0.05, subTotal = 0,grandTotal = 0;
        double paid = 0, balance;
        
        Scanner scan = new Scanner(System.in);
        boolean figureEnter, quitEnter;
        char confirmEnter;
        adult = table[scanTableNumber-1].getAdultCount();
        kid = table[scanTableNumber-1].getChildCount();
        elder = table[scanTableNumber-1].getElderCount();
        //Count 
        countAdult = adult * ProductList.get(0).getpPriceAdults(); // get(1)
        countKid = kid * ProductList.get(0).getpPriceKids();
        countElder = elder * ProductList.get(0).getpPriceElders();
        subTotal = countAdult + countKid + countElder; //Total up 
        grandTotal = (subTotal * SST) + subTotal; //Total + SST
        //membership
        if(member){
            if(dayVisit >= 3){               
                grandTotal = grandTotal - (grandTotal * 0.1); //member discount
            }else{
                grandTotal = grandTotal - (grandTotal * 0.05); //member discount
            }
        }      
        
        //Display method!=class
        displaywhatever(table,ProductList,adult,kid,elder,subTotal,grandTotal,SST,countAdult,countKid,countElder,summarying,scanTableNumber,member,dayVisit);
                                        
        //Balance 
        do{
            System.out.println("Enter the figure to pay :");
            try
            {            
                paid = scan.nextDouble();     
                figureEnter = true;   
                scan.nextLine(); 
            }
            catch (Exception ex){
                System.out.print("Invalid input");
                figureEnter = false;
                scan.nextLine();
            }  
            if(paid <=0 && paid >= 9999.99){
                if(paid <=0)
                {
                    System.out.println("The figure must be positive");
                }else{
                    System.out.println("The figure must be in the range 0 - 9999.99 only");
                }        
                quitEnter = false;
            }else{
                //ask to confirm or not
                do{
                    System.out.printf("Confirm the figure entered (Y/N): ");
                    confirmEnter = scan.next().charAt(0);   
                    scan.nextLine();
                    confirmEnter = Character.toUpperCase(confirmEnter);                
                    if(!(confirmEnter == 'Y' || confirmEnter == 'N')){
                        System.out.println("Just Enter Y to continue the bill and N to not.");
                    }
                }while(!(confirmEnter == 'Y' || confirmEnter == 'N'));
                if(confirmEnter == 'Y'){
                    quitEnter = true;
                    balance = grandTotal-paid;
                    System.out.printf("Balance : %-5.2f",balance);
                }else{
                    quitEnter = false;                
                }
            }    
        }while(!figureEnter || paid <= 0 || paid >= 9999.99 || !quitEnter);
       
        char confirm;
        boolean quitBill;
        //ask to confirm or not
            do{
                System.out.printf("Confirm the bill (Y/N): ");
                confirm = scan.next().charAt(0);   
                scan.nextLine();
                confirm = Character.toUpperCase(confirm);                
                if(!(confirm == 'Y' || confirm == 'N')){
                    System.out.println("Just Enter Y to continue the bill and N to not.");
                }
            }while(!(confirm == 'Y' || confirm == 'N'));
            quitBill = confirm != 'Y';
            
        if(!quitBill){
            summarying[count].setCountAdult(adult);
            summarying[count].setCountKid(kid);
            summarying[count].setCountElder(elder);
            summarying[count].setSubTotal(subTotal);
            summarying[count].setGrandTotal(grandTotal);
            summarying[count].setSST(SST);
            summarying[count].setTotalAdult(countAdult);
            summarying[count].setTotalKid(countKid);
            summarying[count].setTotalElder(countElder);
            summarying[count].setSet('A');
            count++;
            table[scanTableNumber-1].setOccupy("Empty");
            table[scanTableNumber-1].setPersonCount(0);
            table[scanTableNumber-1].setAdultCount(0);
            table[scanTableNumber-1].setChildCount(0);
            table[scanTableNumber-1].setElderCount(0);         
            table[scanTableNumber-1].setComboSet('-');
        }
    }
    
        public static void comboB(tables[] table,ArrayList<Product> ProductList,ArrayList<Member> MemberList,int scanTableNumber,countSummary [] summarying, boolean member, int dayVisit){
        int adult, kid, elder;
        double countAdult, countKid,  countElder;
        double SST = 0.05, subTotal = 0,grandTotal = 0;
        double paid = 0, balance;
        
        Scanner scan = new Scanner(System.in);
        boolean figureEnter, quitEnter;
        char confirmEnter;
        adult = table[scanTableNumber-1].getAdultCount();
        kid = table[scanTableNumber-1].getChildCount();
        elder = table[scanTableNumber-1].getElderCount();
        //Count 
        countAdult = adult * ProductList.get(0).getpPriceAdults(); // get(1)
        countKid = kid * ProductList.get(0).getpPriceKids();
        countElder = elder * ProductList.get(0).getpPriceElders();
        subTotal = countAdult + countKid + countElder; //Total up 
        grandTotal = (subTotal * SST) + subTotal; //Total + SST
        //membership
        if(member){
            if(dayVisit >= 3){               
                grandTotal = grandTotal - (grandTotal * 0.1); //member discount
            }else{
                grandTotal = grandTotal - (grandTotal * 0.05); //member discount
            }
        }
             
        //Display method!=class
        displaywhatever(table,ProductList,adult,kid,elder,subTotal,grandTotal,SST,countAdult,countKid,countElder,summarying,scanTableNumber,member,dayVisit);
                                        
        //Balance 
        do{
            System.out.println("Enter the figure to pay :");
            try
            {            
                paid = scan.nextDouble();     
                figureEnter = true;   
                scan.nextLine(); 
            }
            catch (Exception ex){
                System.out.print("Invalid input");
                figureEnter = false;
                scan.nextLine();
            }  
            if(paid <=0 && paid >= 9999.99){
                if(paid <=0)
                {
                    System.out.println("The figure must be positive");
                }else{
                    System.out.println("The figure must be in the range 0 - 9999.99 only");
                }        
                quitEnter = false;
            }else{
                //ask to confirm or not
                do{
                    System.out.printf("Confirm the figure entered (Y/N): ");
                    confirmEnter = scan.next().charAt(0);   
                    scan.nextLine();
                    confirmEnter = Character.toUpperCase(confirmEnter);                
                    if(!(confirmEnter == 'Y' || confirmEnter == 'N')){
                        System.out.println("Just Enter Y to continue the bill and N to not.");
                    }
                }while(!(confirmEnter == 'Y' || confirmEnter == 'N'));
                if(confirmEnter == 'Y'){
                    quitEnter = true;
                    balance = grandTotal-paid;
                    System.out.printf("Balance : %-5.2f",balance);
                }else{
                    quitEnter = false;                
                }
            }    
        }while(!figureEnter || paid <= 0 || paid >= 9999.99 || !quitEnter);
       
        char confirm;
        boolean quitBill;
        //ask to confirm or not
            do{
                System.out.printf("Confirm the bill (Y/N): ");
                confirm = scan.next().charAt(0);   
                scan.nextLine();
                confirm = Character.toUpperCase(confirm);                
                if(!(confirm == 'Y' || confirm == 'N')){
                    System.out.println("Just Enter Y to continue the bill and N to not.");
                }
            }while(!(confirm == 'Y' || confirm == 'N'));
            quitBill = confirm != 'Y';
            
        if(!quitBill){
            summarying[count].setCountAdult(adult);
            summarying[count].setCountKid(kid);
            summarying[count].setCountElder(elder);
            summarying[count].setSubTotal(subTotal);
            summarying[count].setGrandTotal(grandTotal);
            summarying[count].setSST(SST);
            summarying[count].setTotalAdult(countAdult);
            summarying[count].setTotalKid(countKid);
            summarying[count].setTotalElder(countElder);
            summarying[count].setSet('B');
            count++;
            table[scanTableNumber-1].setOccupy("Empty");
            table[scanTableNumber-1].setPersonCount(0);
            table[scanTableNumber-1].setAdultCount(0);
            table[scanTableNumber-1].setChildCount(0);
            table[scanTableNumber-1].setElderCount(0);         
            table[scanTableNumber-1].setComboSet('-');
        }
    }
        
        public static void comboC(tables[] table,ArrayList<Product> ProductList,ArrayList<Member> MemberList,int scanTableNumber,countSummary [] summarying, boolean member, int dayVisit){
        int adult, kid, elder;
        double countAdult, countKid,  countElder;
        double SST = 0.05, subTotal = 0,grandTotal = 0;
        double paid = 0, balance;
        
        Scanner scan = new Scanner(System.in);
        boolean figureEnter, quitEnter;
        char confirmEnter;
        adult = table[scanTableNumber-1].getAdultCount();
        kid = table[scanTableNumber-1].getChildCount();
        elder = table[scanTableNumber-1].getElderCount();
        //Count 
        countAdult = adult * ProductList.get(0).getpPriceAdults(); // get(1)
        countKid = kid * ProductList.get(0).getpPriceKids();
        countElder = elder * ProductList.get(0).getpPriceElders();
        subTotal = countAdult + countKid + countElder; //Total up 
        grandTotal = (subTotal * SST) + subTotal; //Total + SST
        //membership
        if(member){
            if(dayVisit >= 3){               
                grandTotal = grandTotal - (grandTotal * 0.1); //member discount
            }else{
                grandTotal = grandTotal - (grandTotal * 0.05); //member discount
            }
        }
        
        
        //Display method!=class
        displaywhatever(table,ProductList,adult,kid,elder,subTotal,grandTotal,SST,countAdult,countKid,countElder,summarying,scanTableNumber,member,dayVisit);
                                        
        //Balance 
        do{
            System.out.println("Enter the figure to pay :");
            try
            {            
                paid = scan.nextDouble();     
                figureEnter = true;   
                scan.nextLine(); 
            }
            catch (Exception ex){
                System.out.print("Invalid input");
                figureEnter = false;
                scan.nextLine();
            }  
            if(paid <=0 && paid >= 9999.99){
                if(paid <=0)
                {
                    System.out.println("The figure must be positive");
                }else{
                    System.out.println("The figure must be in the range 0 - 9999.99 only");
                }        
                quitEnter = false;
            }else{
                //ask to confirm or not
                do{
                    System.out.printf("Confirm the figure entered (Y/N): ");
                    confirmEnter = scan.next().charAt(0);   
                    scan.nextLine();
                    confirmEnter = Character.toUpperCase(confirmEnter);                
                    if(!(confirmEnter == 'Y' || confirmEnter == 'N')){
                        System.out.println("Just Enter Y to continue the bill and N to not.");
                    }
                }while(!(confirmEnter == 'Y' || confirmEnter == 'N'));
                if(confirmEnter == 'Y'){
                    quitEnter = true;
                    balance = grandTotal-paid;
                    System.out.printf("Balance : %-5.2f",balance);
                }else{
                    quitEnter = false;                
                }
            }    
        }while(!figureEnter || paid <= 0 || paid >= 9999.99 || !quitEnter);
       
        char confirm;
        boolean quitBill;
        //ask to confirm or not
            do{
                System.out.printf("Confirm the bill (Y/N): ");
                confirm = scan.next().charAt(0);   
                scan.nextLine();
                confirm = Character.toUpperCase(confirm);                
                if(!(confirm == 'Y' || confirm == 'N')){
                    System.out.println("Just Enter Y to continue the bill and N to not.");
                }
            }while(!(confirm == 'Y' || confirm == 'N'));
            quitBill = confirm != 'Y';
            
        if(!quitBill){
            summarying[count].setCountAdult(adult);
            summarying[count].setCountKid(kid);
            summarying[count].setCountElder(elder);
            summarying[count].setSubTotal(subTotal);
            summarying[count].setGrandTotal(grandTotal);
            summarying[count].setSST(SST);
            summarying[count].setTotalAdult(countAdult);
            summarying[count].setTotalKid(countKid);
            summarying[count].setTotalElder(countElder);
            summarying[count].setSet('C');
            count++;
            table[scanTableNumber-1].setOccupy("Empty");
            table[scanTableNumber-1].setPersonCount(0);
            table[scanTableNumber-1].setAdultCount(0);
            table[scanTableNumber-1].setChildCount(0);
            table[scanTableNumber-1].setElderCount(0);         
            table[scanTableNumber-1].setComboSet('-');
        }
    }
    
        public static void comboD(tables[] table,ArrayList<Product> ProductList,ArrayList<Member> MemberList,int scanTableNumber,countSummary [] summarying, boolean member, int dayVisit){
        int adult, kid, elder;
        double countAdult, countKid,  countElder;
        double SST = 0.05, subTotal = 0,grandTotal = 0;
        double paid = 0, balance;
        
        Scanner scan = new Scanner(System.in);
        boolean figureEnter, quitEnter;
        char confirmEnter;
        adult = table[scanTableNumber-1].getAdultCount();
        kid = table[scanTableNumber-1].getChildCount();
        elder = table[scanTableNumber-1].getElderCount();
        //Count 
        countAdult = adult * ProductList.get(0).getpPriceAdults(); // get(1)
        countKid = kid * ProductList.get(0).getpPriceKids();
        countElder = elder * ProductList.get(0).getpPriceElders();
        subTotal = countAdult + countKid + countElder; //Total up 
        grandTotal = (subTotal * SST) + subTotal; //Total + SST
        //membership
        if(member){
            if(dayVisit >= 3){               
                grandTotal = grandTotal - (grandTotal * 0.1); //member discount
            }else{
                grandTotal = grandTotal - (grandTotal * 0.05); //member discount
            }
        }
        
        
        //Display method!=class
        displaywhatever(table,ProductList,adult,kid,elder,subTotal,grandTotal,SST,countAdult,countKid,countElder,summarying,scanTableNumber,member,dayVisit);
                                        
        //Balance 
        do{
            System.out.println("Enter the figure to pay :");
            try
            {            
                paid = scan.nextDouble();     
                figureEnter = true;   
                scan.nextLine(); 
            }
            catch (Exception ex){
                System.out.print("Invalid input");
                figureEnter = false;
                scan.nextLine();
            }  
            if(paid <=0 && paid >= 9999.99){
                if(paid <=0)
                {
                    System.out.println("The figure must be positive");
                }else{
                    System.out.println("The figure must be in the range 0 - 9999.99 only");
                }        
                quitEnter = false;
            }else{
                //ask to confirm or not
                do{
                    System.out.printf("Confirm the figure entered (Y/N): ");
                    confirmEnter = scan.next().charAt(0);   
                    scan.nextLine();
                    confirmEnter = Character.toUpperCase(confirmEnter);                
                    if(!(confirmEnter == 'Y' || confirmEnter == 'N')){
                        System.out.println("Just Enter Y to continue the bill and N to not.");
                    }
                }while(!(confirmEnter == 'Y' || confirmEnter == 'N'));
                if(confirmEnter == 'Y'){
                    quitEnter = true;
                    balance = grandTotal-paid;
                    System.out.printf("Balance : %-5.2f",balance);
                }else{
                    quitEnter = false;                
                }
            }    
        }while(!figureEnter || paid <= 0 || paid >= 9999.99 || !quitEnter);
       
        char confirm;
        boolean quitBill;
        //ask to confirm or not
            do{
                System.out.printf("Confirm the bill (Y/N): ");
                confirm = scan.next().charAt(0);   
                scan.nextLine();
                confirm = Character.toUpperCase(confirm);                
                if(!(confirm == 'Y' || confirm == 'N')){
                    System.out.println("Just Enter Y to continue the bill and N to not.");
                }
            }while(!(confirm == 'Y' || confirm == 'N'));
            quitBill = confirm != 'Y';
            
        if(!quitBill){
            summarying[count].setCountAdult(adult);
            summarying[count].setCountKid(kid);
            summarying[count].setCountElder(elder);
            summarying[count].setSubTotal(subTotal);
            summarying[count].setGrandTotal(grandTotal);
            summarying[count].setSST(SST);
            summarying[count].setTotalAdult(countAdult);
            summarying[count].setTotalKid(countKid);
            summarying[count].setTotalElder(countElder);
            summarying[count].setSet('D');
            count++;
            table[scanTableNumber-1].setOccupy("Empty");
            table[scanTableNumber-1].setPersonCount(0);
            table[scanTableNumber-1].setAdultCount(0);
            table[scanTableNumber-1].setChildCount(0);
            table[scanTableNumber-1].setElderCount(0);         
            table[scanTableNumber-1].setComboSet('-');
        }
    }
}
