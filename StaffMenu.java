/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment;

import java.util.Scanner;
import java.lang.String;
/**
 *
 * @author Asus
 */
public class StaffMenu {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.println("===================================================");
        System.out.println("| 1 | Add Staff ");
        System.out.println("===================================================");
        System.out.println("| 2 | Modify Staff ");
        System.out.println("===================================================");
        System.out.println("| 3 | Exit ");
        System.out.println("===================================================");
        
 
        int selection= scn.nextInt();
        
        switch(selection){
            case 1:
                Staff[] addS = new Staff[10];
                addS[0] = new Staff("ABC123", "lee", 20201111, 3000.00, "abc123");
        
                for (int i = 0; i < addS.length; i++) {

                    System.out.print(addS[i]);
                    if (addS[i]==null) {
                        addNewStaff(addS,i);
                        break;
                    } 
                }
                break;
        
            case 2:
        }
        
        Staff[] addS = new Staff[10];
 /*       addS[0] = new Staff("ABC123", "lee", 20201111, 3000.00, "abc123");*/
        

    }

    public static void addNewStaff(Staff[] addS, int i) {
        
        Scanner scn = new Scanner(System.in);
        System.out.print("Please enter the ID :");
        String staffID = scn.nextLine();

        System.out.print("Please enter the Name :");
        String staffName = scn.nextLine();

        System.out.print("Please enter the Date joined :");
        int dateJoined = scn.nextInt();

        System.out.print("Please enter the Salary :");
        double salary = scn.nextDouble();
        scn.nextLine();
 
        System.out.print("Please set the password :");
        String password = scn.nextLine();

        addS[i]= new Staff(staffID,staffName,dateJoined,salary,password);
    }
}