package com.geomhwein.go.creator.service;

import com.geomhwein.go.command.HomeworkVO;
import com.geomhwein.go.command.QuestionVO;
import com.geomhwein.go.command.SubmissionVO;
import com.geomhwein.go.command.UserDetailsVO;

import java.util.List;

import com.geomhwein.go.command.EducationGroupVO;

public interface CreatorService {

	public void makeHomework(HomeworkVO vo);

	public int getApplyCount();

	public EducationGroupVO getApply(int i);

	public List<SubmissionVO> getHomeworkDone(String userId);

	public List<UserDetailsVO> getAllStudent();

	public void deleteApply(int aplyNo);

	public List<QuestionVO> getQuestionList(String userId);

	public QuestionVO getQuestion(int qstnNo);

	public void addAnswer(QuestionVO vo);

	
	public SubmissionVO getSubmission(int subNo);
	
	//정답처리 로직
	public int getUserScore(String userId);
	public void setUserScore(String userId, int newScore);
	public void deleteAns(int subNo);

	

}
