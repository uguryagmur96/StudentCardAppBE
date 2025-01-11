package com.bilgeadam.service;

import com.bilgeadam.dto.request.SaveContributionRequestDto;
import com.bilgeadam.dto.request.UpdateContributionRequestDto;
import com.bilgeadam.dto.response.GetContributionResponseDto;
import com.bilgeadam.dto.response.MessageResponse;
import com.bilgeadam.exceptions.CardServiceException;
import com.bilgeadam.exceptions.ErrorType;
import com.bilgeadam.mapper.IContributionMapper;
import com.bilgeadam.repository.IContributionRepository;
import com.bilgeadam.repository.entity.Contribution;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContributionService extends ServiceManager<Contribution, String> {
    private final IContributionRepository contributionRepository;
    private final JwtTokenManager jwtTokenManager;

    public ContributionService(IContributionRepository contributionRepository, JwtTokenManager jwtTokenManager) {
        super(contributionRepository);
        this.contributionRepository = contributionRepository;
        this.jwtTokenManager = jwtTokenManager;
    }
    public boolean saveContribution(SaveContributionRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isPresent()) {
            Contribution addContribution = IContributionMapper.INSTANCE.fromSaveContributionRequestDtoToContribution(dto);
            addContribution.setTotalScoreContribution(calculateTotalScore(addContribution.getDocumentationForBacklogNote(),
            addContribution.getIncorrectCodeOrDisplayMessageNote(),addContribution.getResearchNote(),addContribution.getIntraTeamTrainingNote()));
            addContribution.setStudentId(studentId.get());
            save(addContribution);
            return true;
        }
        else {throw new CardServiceException(ErrorType.INVALID_TOKEN);}
    }
    public MessageResponse updateContribution(UpdateContributionRequestDto dto) {
        Optional<String> studentId = jwtTokenManager.getIdFromToken(dto.getStudentToken());
        if (studentId.isEmpty()) {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);
        }
        Optional<Contribution> cont = contributionRepository.findByStudentId(studentId.get());
        if (cont.isEmpty()){
           throw new CardServiceException(ErrorType.CONTRIBUTION_NOT_FOUND);
        }
        Contribution contribution=cont.get();
        contribution.setIncorrectCodeOrDisplayMessageNote(dto.getIncorrectCodeOrDisplayMessageNote());
        contribution.setDocumentationForBacklogNote(dto.getDocumentationForBacklogNote());
        contribution.setResearchNote(dto.getResearchNote());
        contribution.setIntraTeamTrainingNote(dto.getIntraTeamTrainingNote());
        contribution.setTotalScoreContribution(calculateTotalScore(contribution.getDocumentationForBacklogNote(),
        contribution.getIncorrectCodeOrDisplayMessageNote(),contribution.getResearchNote(),contribution.getIntraTeamTrainingNote()));
        update(contribution);
        return new MessageResponse("Başarıyla kaydedildi");
    }
    public GetContributionResponseDto getContribution(String studentId) {
        Optional<Contribution> cont=contributionRepository.findByStudentId(studentId);
        if (cont.isEmpty()) {
           return null;
        }
        else {GetContributionResponseDto dto=IContributionMapper.INSTANCE.fromContributionToGetContributionResponseDto(cont.get());
            return dto;
        }
    }
    public Double calculateAndGetTotalScoreContribution(String studentId) {
        if (studentId.isEmpty()) {
            throw new CardServiceException(ErrorType.INVALID_TOKEN);}
        Optional<Contribution> cont=contributionRepository.findByStudentId(studentId);
        if (cont.isEmpty()) {
            return null; }
        if (cont.get().getDocumentationForBacklogNote()==0){
            return cont.get().getDocumentationForBacklogNote();
        }
        Contribution contribution=cont.get();
       Double totalScore=calculateTotalScore(contribution.getDocumentationForBacklogNote(),contribution.getDocumentationForBacklogNote()
        ,contribution.getResearchNote(),contribution.getIntraTeamTrainingNote());
        return totalScore;
    }
    public Double calculateTotalScore(Double backLogNote,Double dpMessageNote, Double researchNote, Double teamTrainingNote){
        Double percentage=0.05;
        if(dpMessageNote ==null){
            dpMessageNote=0.0;
        }
        if(researchNote ==null){
            researchNote=0.0;
        }
        if(teamTrainingNote ==null){
            teamTrainingNote=0.0;
        }
        Double totalScore =(backLogNote)+(dpMessageNote*percentage)+(researchNote*percentage)+(teamTrainingNote*percentage);
        if(totalScore>100){ totalScore=100.0; }
        return  totalScore;
    }
}
