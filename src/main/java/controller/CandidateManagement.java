/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import common.DataInput;
import common.FileHandler;
import java.util.ArrayList;
import java.util.List;
import model.Candidate;
import model.Experience;
import model.Fresher;
import model.Intern;
import model.Skill;
import view.CandidateView;
import view.InterfaceView;

/**
 *
 * @author Dang Phat
 */
public class CandidateManagement {
    
    private CandidateManagement(){
        throw new UnsupportedOperationException();
    }

    private static final InterfaceView view = new InterfaceView();
    private static final CandidateView candidateView = new CandidateView();
    private static final String CANDIDATE = "candidate.txt";
    private static final String CANDIDATE_SKILL = "skill candidate.txt";

    private static void list() {
        List<Experience> listExperiences = FileHandler.getExperience();
        List<Fresher> listFreshers = FileHandler.getFresher();
        List<Intern> listInterns = FileHandler.getIntern();
        candidateView.listExperiences(listExperiences);
        candidateView.listFreshers(listFreshers);
        candidateView.listInterns(listInterns);
    }

    private static void add() {
        List<String> listCandidates = FileHandler.getAllDataInFile(CANDIDATE);
        List<String> listCandidateSkills = FileHandler.getAllDataInFile(CANDIDATE_SKILL);
        Candidate candidate = candidateView.inputCandidateInformation(listCandidates);
        listCandidates.add(candidate.saveFormat());
        for (Skill skill : candidate.getListSkills()) {
            listCandidateSkills.add(candidate.getId() + "|" + skill.getSkillName() + "|" + skill.getYearOfExp());
        }
        FileHandler.writeFile(CANDIDATE, listCandidates);
        FileHandler.writeFile(CANDIDATE_SKILL, listCandidateSkills);
        System.out.println("Add successfully!");
    }

    private static void upadte() {
        List<String> listCandidates = FileHandler.getAllDataInFile(CANDIDATE);
        List<String> listCandidateSkills = FileHandler.getAllDataInFile(CANDIDATE_SKILL);
        ArrayList<String> listCandidateSkillsUpdated = new ArrayList<>();
        int candidateID = DataInput.checkInputIntLimit("Enter candidate ID to update: ", 1, listCandidates.size());
        for (String candidate : listCandidates) {
            if (Integer.parseInt(candidate.split("\\|")[0]) == candidateID) {
                Candidate candidateInfo = candidateView.inputCandidateInformation(listCandidates);
                String candidateUpdated = candidateID + candidateInfo.saveFormat().substring(candidate.indexOf("|"));
                listCandidates.set(candidateID - 1, candidateUpdated);
                for (String candidateSkill : listCandidateSkills) {
                    if (Integer.parseInt(candidateSkill.split("\\|")[0]) != candidateID) {
                        listCandidateSkillsUpdated.add(candidateSkill);
                    }
                }
                for (Skill skill : candidateInfo.getListSkills()) {
                    listCandidateSkillsUpdated.add(candidateID + "|" + skill.getSkillName() + "|" + skill.getYearOfExp());
                }
                break;
            }
        }
        FileHandler.writeFile(CANDIDATE, listCandidates);
        FileHandler.writeFile(CANDIDATE_SKILL, listCandidateSkillsUpdated);
        System.out.println("Update successfully!");
    }

    private static void delete() {
        List<String> listCandidates = FileHandler.getAllDataInFile(CANDIDATE);
        List<String> listCandidateSkills = FileHandler.getAllDataInFile(CANDIDATE_SKILL);
        ArrayList<String> listCandidateSkillsUpdated = new ArrayList<>();
        int candidateID = DataInput.checkInputIntLimit("Enter candidate ID to delete: ", 1, listCandidates.size());
        for (String candidate : listCandidates) {
            if (Integer.parseInt(candidate.split("\\|")[0]) == candidateID) {
                listCandidates.remove(candidateID - 1);
                break;
            }
        }
        for (int i = candidateID - 1; i < listCandidates.size(); i++) {
            String candidateUpdated = (i + 1) + listCandidates.get(i).substring(listCandidates.get(i).indexOf("|"));
            listCandidates.set(i, candidateUpdated);
        }
        for (String candidateSkill : listCandidateSkills) {
            int nextCandidateID = Integer.parseInt(candidateSkill.split("\\|")[0]);
            if (nextCandidateID != candidateID) {
                if(nextCandidateID > candidateID){
                    listCandidateSkillsUpdated.add((nextCandidateID - 1) + "|" + candidateSkill.split("\\|")[1] + "|" + candidateSkill.split("\\|")[2]);
                } else {
                    listCandidateSkillsUpdated.add(candidateSkill);
                }                
            }
        }
        FileHandler.writeFile(CANDIDATE, listCandidates);
        FileHandler.writeFile(CANDIDATE_SKILL, listCandidateSkillsUpdated);
        System.out.println("Delete successfully!");
    }

    public static void candidateManagementController() {
        int CandidateManagementChoice = view.candidateManagement();
        switch (CandidateManagementChoice) {
            case 1:
                list();
                break;
            case 2:
                add();
                break;
            case 3:
                upadte();
                break;
            case 4:
                delete();
                break;
            default:
                break;
        }
    }
}
