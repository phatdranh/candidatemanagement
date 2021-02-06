/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import common.FileHandler;
import java.util.List;
import view.InterfaceView;
import view.CandidateSkillView;

/**
 *
 * @author Dang Phat
 */
public class CandidateSkillInfoManagement {
    
    private CandidateSkillInfoManagement(){
        throw new UnsupportedOperationException();
    }

    private static final InterfaceView view = new InterfaceView();
    private static final String SKILL_CANDIDATE = "skill candidate.txt";
    private static final String CANDIDATE = "candidate.txt";
    private static final String SKILL = "skill.txt";

    private static void add() {
        List<String> listCandidateSkills = FileHandler.getAllDataInFile(SKILL_CANDIDATE);
        List<String> listCandidates = FileHandler.getAllDataInFile(CANDIDATE);
        List<String> listSkills = FileHandler.getAllDataInFile(SKILL);
        int candidateID = CandidateSkillView.getCandidateId(listCandidates);
        boolean isContinue = true;
        while (isContinue) {
            String skillName = CandidateSkillView.getSkillName(listSkills);
            for (int i = 0; i < listSkills.size(); i++) {
                if (skillName.equalsIgnoreCase(listSkills.get(i).split("\\|")[1])) {
                    int yearOfExp = CandidateSkillView.getExperience(1000);
                    listCandidateSkills.add(candidateID + "|" + skillName + "|" + yearOfExp);
                    isContinue = false;
                }
            }
            if (isContinue) {
                System.out.println("Do not have this skill! Please enter again!");
            }
        }
        FileHandler.writeFile(SKILL_CANDIDATE, listCandidateSkills);
    }

    private static void update() {
        List<String> listCandidateSkills = FileHandler.getAllDataInFile(SKILL_CANDIDATE);
        List<String> listCandidates = FileHandler.getAllDataInFile(CANDIDATE);
        List<String> listSkills = FileHandler.getAllDataInFile(SKILL);
        int candidateID = CandidateSkillView.getCandidateId(listCandidates);
        boolean isContinue = true;
        while (isContinue) {
            String skillName = CandidateSkillView.getSkillName(listSkills);
            for (int i = 0; i < listCandidateSkills.size(); i++) {
                if (candidateID == Integer.parseInt(listCandidateSkills.get(i).split("\\|")[0])
                        && skillName.equalsIgnoreCase(listCandidateSkills.get(i).split("\\|")[1])) {
                    int yearOfExp = CandidateSkillView.getExperience(1000);
                    listCandidateSkills.set(i, candidateID + "|" + skillName + "|" + yearOfExp);
                    isContinue = false;
                }
            }
            if (isContinue) {
                System.out.println("Candidate does not have this skill! Please enter again!");
            }
        }
        FileHandler.writeFile(SKILL_CANDIDATE, listCandidateSkills);
    }

    private static void delete() {
        List<String> listCandidateSkills = FileHandler.getAllDataInFile(SKILL_CANDIDATE);
        List<String> listCandidates = FileHandler.getAllDataInFile(CANDIDATE);
        List<String> listSkills = FileHandler.getAllDataInFile(SKILL);
        int candidateID = CandidateSkillView.getCandidateId(listCandidates);
        boolean isContinue = true;
        while (isContinue) {
            String skillName = CandidateSkillView.getSkillName(listSkills);
            for (int i = 0; i < listCandidateSkills.size(); i++) {
                if (candidateID == Integer.parseInt(listCandidateSkills.get(i).split("\\|")[0])
                        && skillName.equalsIgnoreCase(listCandidateSkills.get(i).split("\\|")[1])) {
                    listCandidateSkills.remove(listCandidateSkills.get(i));                    
                    isContinue = false;
                    break;
                }
            }
            if (isContinue) {
                System.out.println("Candidate does not have this skill! Please enter again!");
            }
        }
        FileHandler.writeFile(SKILL_CANDIDATE, listCandidateSkills);
        System.out.println("Delete successfully!");
    }

    public static void candidateSkill() {
        int candidateSkillInfoChoice = view.candidateSkillInformationManagement();
        switch (candidateSkillInfoChoice) {
            case 1:
                add();
                break;
            case 2:
                update();
                break;
            case 3:
                delete();
                break;
            default:
                break;
        }

    }
}
