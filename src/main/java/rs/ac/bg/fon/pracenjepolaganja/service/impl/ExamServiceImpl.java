package rs.ac.bg.fon.pracenjepolaganja.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.pracenjepolaganja.dao.ExamRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.ResultExamRepository;
import rs.ac.bg.fon.pracenjepolaganja.dao.TestRepository;
import rs.ac.bg.fon.pracenjepolaganja.dto.*;
import rs.ac.bg.fon.pracenjepolaganja.entity.Exam;
import rs.ac.bg.fon.pracenjepolaganja.entity.ResultExam;
import rs.ac.bg.fon.pracenjepolaganja.entity.primarykeys.ResultExamPK;
import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;
import rs.ac.bg.fon.pracenjepolaganja.service.ServiceInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * Reference variable of TestRepository class.
     */
    private TestRepository testRepository;

    /**
     * References to the ModelMapper.
     * Maps DTO objects to entity objects and vice versa.
     */
    private ModelMapper modelMapper;

    @Autowired
    public ExamServiceImpl(ExamRepository examRepository,ResultExamRepository resultExamRepository,TestRepository testRepository, ModelMapper modelMapper){
        this.examRepository = examRepository;
        this.resultExamRepository = resultExamRepository;
        this.modelMapper = modelMapper;
        this.testRepository = testRepository;
    }

    @Override
    public List<ExamDTO> findAll() {
        List<Exam> exams = examRepository.findAll();
        List<ExamDTO> examsDTO = new ArrayList<>();
        for(Exam exam:exams){
            TestDTO testDTO = modelMapper.map(exam.getTest(),TestDTO.class);
            ExamDTO examDTO = modelMapper.map(exam,ExamDTO.class);
            ProfessorDTO professorDTO = modelMapper.map(exam.getTest().getAuthor(),ProfessorDTO.class);
            testDTO.setAuthor(professorDTO);
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
            ProfessorDTO professorDTO = modelMapper.map(exam.get().getTest().getAuthor(),ProfessorDTO.class);
            testDTO.setAuthor(professorDTO);
            examDTO = modelMapper.map(exam.get(),ExamDTO.class);
            examDTO.setTest(testDTO);
            Collection<ResultExamDTO> results = getResults((Integer) id);
            examDTO.setResults(results);
        }
        else{
            throw new NotFoundException("Polaganje nije pronadjeno");
        }
        return examDTO;
    }

    @Override
    public ExamDTO save(ExamDTO examDTO) throws Exception {
        if(examDTO==null){
            throw new NullPointerException("Polaganje ne moze biti null");
        }
        ExamDTO newExamDTO = examDTO;
        Exam exam = modelMapper.map(examDTO,Exam.class);
        Exam savedExam = examRepository.save(exam);
        if(newExamDTO.getResults()!=null && !newExamDTO.getResults().isEmpty()){
            Collection<ResultExam> results = newExamDTO.getResults().stream().map(resultExamDTO -> modelMapper.map(resultExamDTO, ResultExam.class))
                    .collect(Collectors.toList());
            for(ResultExam resultExam:results){
                resultExam.getResultExamPK().setExamId(savedExam.getId());
                resultExamRepository.save(resultExam);
            }
        }
        return modelMapper.map(savedExam, ExamDTO.class);
    }

    @Override
    public ExamDTO update(ExamDTO examDTO) throws Exception {
        if(examDTO==null){
            throw new NullPointerException("Polaganje ne moze biti null");
        }
        Optional<Exam> dbExam = examRepository.findById(examDTO.getId());
        if(dbExam.isPresent()) {
            Exam exam = modelMapper.map(examDTO,Exam.class);
            if(examDTO.getResults()!=null){
                Collection<ResultExam> results = examDTO.getResults().stream().map(resultExamDTO -> modelMapper.map(resultExamDTO, ResultExam.class))
                        .collect(Collectors.toList());
                exam.setResultExamCollection(results);
            }
            Exam savedExam = examRepository.save(exam);
            return modelMapper.map(savedExam, ExamDTO.class);
        }
        else{
            throw new NotFoundException("Polaganje nije pronadjeno");
        }
    }

    @Override
    public void deleteById(Object id) throws NotFoundException {
        if(!examRepository.findById((Integer) id).isPresent()){
            throw new NotFoundException("Polaganje nije pronadjeno");
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
            return new ArrayList<>();
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
            throw new NullPointerException("ResultExam ne moze biti null");
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
            throw new NotFoundException("Rezultat polaganja nije pronadjen");
        }
        resultExamRepository.deleteById(new ResultExamPK(examId,studentId));
    }

    /**
     * Retrieves students of exam.
     *
     * @param examId id of exam whose students are needed
     * @return list of exam students
     * @throws NotFoundException if exam with given id doesn't have students
     */
    public List<StudentDTO> getStudents(Integer examId) throws NotFoundException  {
        List<ResultExam> resultsExam = resultExamRepository.findByExamId(examId);
        if(resultsExam.isEmpty()){
            throw new NotFoundException("Studenti polaganja sa id-em: " + examId + " nisu pronadjeni");
        }

        List<StudentDTO> students = new ArrayList<>();
        for(ResultExam resultExam : resultsExam){
            StudentDTO studentDTO = modelMapper.map(resultExam.getStudent(),StudentDTO.class);
            students.add(studentDTO);
        }
        return students;
    }
}
