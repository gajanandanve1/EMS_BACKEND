package com.yash.ems.serviceimpl;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.ems.model.Evaluation;
import com.yash.ems.repo.EvaluationRepo;
import com.yash.ems.service.EvaluationService;

@Service
public class EvaluationServiceImpl implements EvaluationService {

	@Autowired
	private EvaluationRepo evaluationRepo;
	
	@Override
	public Evaluation addEvatuationDetails(Evaluation eva) {
		
		return evaluationRepo.save(eva);
	}

	@Override
	public List<Evaluation> getAllEvaluation() {
		// TODO Auto-generated method stub
		return evaluationRepo.findAll();
	}

	@Override
	public void deleteEmployee(Integer evaluationId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Evaluation findByEmployeeId(Integer evaluationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Evaluation updateEvaluation(Evaluation evaluation, Integer evaluationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Evaluation findByEvaluationId(Integer evaluationId) {
		
		return evaluationRepo.findById(evaluationId).get();
	}

}
