/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import common.DataInput;
import common.FileHandler;
import java.util.List;
import model.User;
import view.AccountView;
import view.InterfaceView;

/**
 *
 * @author Dang Phat
 */
public class AccountManagement {
    
    private AccountManagement(){
        throw new UnsupportedOperationException();
    }

    private static final InterfaceView view = new InterfaceView();

    private static void list() {
        List<String> listAccounts = FileHandler.getListAccounts();
        System.out.println("List accounts:");
        System.out.println("Usercode\tUsername\tType");
        for (String account : listAccounts) {
            String[] accInfo = account.split("\\|");
            String type = accInfo[3].equals("1") ? "Admin" : "Memeber";
            System.out.println(accInfo[0] + "\t\t" + accInfo[1] + "\t\t" + type);
        }
    }

    private static void add() {
        List<String> listAccounts = FileHandler.getListAccounts();
        User newUser = AccountView.createUserInfo(listAccounts);
        listAccounts.add(newUser.getUserCode() + "|" + newUser.getUserName() + "|" + newUser.getPassword() + "|" + newUser.getType());
        FileHandler.saveAccount(listAccounts);
        System.out.println("Add account successfully!");
    }

    private static void update() {
        List<String> listAccounts = FileHandler.getListAccounts();
        User newUser = AccountView.updateUser(listAccounts);
        for (int i = 0; i < listAccounts.size(); i++) {
            if (listAccounts.get(i).split("\\|")[0].equals(newUser.getUserCode() + "")) {
                listAccounts.set(i, listAccounts.get(i).substring(0, listAccounts.get(i).lastIndexOf("|") + 1) + newUser.getType());
                break;
            }
        }
        FileHandler.saveAccount(listAccounts);
        System.out.println("Update successfully!");
    }

    private static void delete() {
        List<String> listAccounts = FileHandler.getListAccounts();
        int userCode = DataInput.checkInputIntLimit("Enter user code to delete: ", 1, listAccounts.size());
        for (String account : listAccounts) {
            if (Integer.parseInt(account.split("\\|")[0]) == userCode) {
                listAccounts.remove(userCode - 1);
                break;
            }
        }
        for (int i = userCode - 1; i < listAccounts.size(); i++) {
            String candidateUpdated = (i + 1) + listAccounts.get(i).substring(listAccounts.get(i).indexOf("|"));
            listAccounts.set(i, candidateUpdated);
        }
        FileHandler.saveAccount(listAccounts);
        System.out.println("Delete sucessfully!");
    }

    private static void changePassword() {
        List<String> listAccounts = FileHandler.getListAccounts();
        User newUser = AccountView.changePassword(listAccounts);
        for (int i = 0; i < listAccounts.size(); i++) {
            String[] accInfo = listAccounts.get(i).split("\\|");
            if (accInfo[0].equals(newUser.getUserCode() + "")) {
                listAccounts.set(i, accInfo[0] + "|" + accInfo[1] + "|" + newUser.getPassword() + "|" + accInfo[3]);
                break;
            }
        }
        FileHandler.saveAccount(listAccounts);
        System.out.println("Change password successfully!");
    }

    public static void userManagementController() {
        int userManagementChoice = view.userManagement();
        switch (userManagementChoice) {
            case 1:
                list();
                break;
            case 2:
                add();
                break;
            case 3:
                update();
                break;
            case 4:
                delete();
                break;
            case 5:
                changePassword();
                break;
            default:
                break;
        }
    }
}
