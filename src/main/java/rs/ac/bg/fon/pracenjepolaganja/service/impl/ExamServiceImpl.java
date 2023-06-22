package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.ExamRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.ResultExamRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.*;
import rs.ac.bg.fon.pracenjepolaganja.entity.Exam;
import rs.ac.bg.fon.pracenjepolaganja.entity.ResultExam;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.ResultExamPK;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents implementation of service interface with Exam entity.
 * T parameter is provided with ExamDTO.
 *
 * @author Vuk Manojlovic
 */
@Service
public class ExamServiceImpl implements ServiceInterface<ExamDTO> {

    /**
     * Reference variable of ExamRepository class.
     */
    private ExamRepository examRepository;

    /**
     * Reference variable of ResultExamRepository class.
     */
    private ResultExamRepository resultExamRepository;

    /**
     * References to the ModelMapper.
     * Maps DTO objects to entity objects and vice versa.
     */
    private ModelMapper modelMapper;

    @Autowired
    public ExamServiceImpl(ExamRepository examRepository,ResultExamRepository resultExamRepository, ModelMapper modelMapper){
        this.examRepository = examRepository;
        this.resultExamRepository = resultExamRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ExamDTO> findAll() {
        List<Exam> exams = examRepository.findAll();
        List<ExamDTO> examsDTO = new ArrayList<>();
        for(Exam exam:exams){
            TestDTO testDTO = modelMapper.map(exam.getTest(),TestDTO.class);
            ExamDTO examDTO = modelMapper.map(exam,ExamDTO.class);
            ProfessorDTO professorDTO = modelMapper.map(exam.getTest().getAuthor(),ProfessorDTO.class);
            testDTO.setProfessor(professorDTO);
            examDTO.setTest(testDTO);
            examsDTO.add(examDTO);
        }
        return examsDTO;
    }

    @Override
    public ExamDTO findById(Object id) throws NotFoundException {
        Optional<Exam> exam = examRepository.findById((Integer) id);
        ExamDTO examDTO;
        if(exam.isPresent()){
            TestDTO testDTO = modelMapper.map(exam.get().getTest(),TestDTO.class);
            examDTO = modelMapper.map(exam.get(),ExamDTO.class);
            ProfessorDTO professorDTO = modelMapper.map(exam.get().getTest().getAuthor(),ProfessorDTO.class);
            testDTO.setProfessor(professorDTO);
            examDTO.setTest(testDTO);
        }
        else{
            throw new NotFoundException("Did not find Exam with id: " + id);
        }
        return examDTO;
    }

    @Override
    public ExamDTO save(ExamDTO examDTO) {
        if(examDTO==null){
            throw new NullPointerException("Exam can't be null");
        }
        Exam exam = examRepository.save(modelMapper.map(examDTO,Exam.class));
        return modelMapper.map(exam,ExamDTO.class);
    }

    @Override
    public void deleteById(Object id) throws NotFoundException {
        if(!examRepository.findById((Integer) id).isPresent()){
            throw new NotFoundException("Did not find Exam with id: " + id);
        }
        examRepository.deleteById((Integer) id);
    }

    /**
     * Retrieves results of exam.
     * Results are sent back in DTO form.
     *
     * @param id of exam whose results are needed
     * @return list of ResultExamDTO objects
     * @throws NotFoundException if ResultExam entities with given exam id does not exist in database
     */
    public List<ResultExamDTO> getResults(Integer id) throws NotFoundException {
        List<ResultExam> resultsExam = resultExamRepository.findByExamId(id);
        if(resultsExam.isEmpty()){
            throw new NotFoundException("Did not find ResultExam with examId: " + id);
        }
        List<ResultExamDTO> resultsExamDTO = new ArrayList<>();
        for(ResultExam resultExam:resultsExam){
            StudentDTO studentDTO = modelMapper.map(resultExam.getStudent(),StudentDTO.class);
            ExamDTO examDTO = modelMapper.map(resultExam.getExam(),ExamDTO.class);
            TestDTO testDTO = modelMapper.map(resultExam.getExam().getTest(),TestDTO.class);
            examDTO.setTest(testDTO);

            ResultExamDTO resultExamDTO = modelMapper.map(resultExam,ResultExamDTO.class);
            resultExamDTO.setExam(examDTO);
            resultExamDTO.setStudent(studentDTO);

            resultsExamDTO.add(resultExamDTO);
        }

        return resultsExamDTO;
    }

    /**
     * Saves result of exam.
     * ResultExam that is going to be saved is in DTO form.
     * Connects one exam with another student.
     *
     * @param resultExamDTO resultExam in DTO form that needs to be saved
     * @return saved resultExam entity in DTO form
     * @throws NullPointerException if provided resultExamDTO is null
     */
    public ResultExamDTO saveResultExam(ResultExamDTO resultExamDTO) {
        if(resultExamDTO == null){
            throw new NullPointerException("ResultExam can't be null");
        }
        ResultExam resultExam = resultExamRepository.save(modelMapper.map(resultExamDTO,ResultExam.class));
        return modelMapper.map(resultExam,ResultExamDTO.class);
    }

    /**
     * Deletes result of exam.
     *
     * @param studentId id of student whose result is going to be deleted.
     * @param examId id of exam whose result is going to be deleted.
     * @throws NotFoundException if resultExam with given ids does not exist in database
     */
    public void deleteResultExam(Integer studentId, Integer examId) throws NotFoundException {
        if(!resultExamRepository.findById(new ResultExamPK(examId,studentId)).isPresent()){
            throw new NotFoundException("Did not find ResultExam with id: " + new ResultExamPK(examId,studentId));
        }
        resultExamRepository.deleteById(new ResultExamPK(examId,studentId));
    }
}
