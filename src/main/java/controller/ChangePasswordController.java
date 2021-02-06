/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import common.FileHandler;
import model.User;
import view.InterfaceView;

/**
 *
 * @author Dang Phat
 */
public class ChangePasswordController {
    
    private ChangePasswordController(){
        throw new UnsupportedOperationException();
    }

    public static void changePassword(User user) {
        InterfaceView view = new InterfaceView();
        String newPassword = view.changePassword(user);
        FileHandler.changePassword(user, newPassword);
    }
}
