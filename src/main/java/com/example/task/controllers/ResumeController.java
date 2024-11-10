package com.example.task.controllers;

import com.example.task.models.Resume;
import com.example.task.models.dto.ResumeDTO;
import com.example.task.models.enums.Category;
import com.example.task.models.enums.Decide;
import com.example.task.services.ResumeService;
import com.example.task.util.Response;
import com.example.task.util.ResumeValidation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.example.task.util.ErrorUtil.returnErrorsToClient;

/**
 * Rest контроллер для работы с Resume
 */
@RestController
@RequestMapping("api/v1/resume")
@Slf4j
public class ResumeController {

    private final ResumeService resumeService;
    private final ResumeValidation resumeValidation;

    @Autowired
    public ResumeController(ResumeService resumeService, ResumeValidation resumeValidation) {
        this.resumeService = resumeService;
        this.resumeValidation = resumeValidation;
    }

    /**
     * Обрабатывает Post-запрос для сохранения и категоризации резюме
     * @param resume ResumeDTO, прошедший валидацию
     * @param bindingResult объект, который используется для хранения результатов валидации
     * @return ResponseEntity с созданным объектом Response и статусом HTTP 201 (Created)
     * @throws Exception
     */
    @PostMapping("/add")
    public ResponseEntity<Response> postResume(@Valid @RequestBody ResumeDTO resume, BindingResult bindingResult) throws Exception {
        resumeValidation.validate(resumeService.parseByResume(resume), bindingResult);
        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }
        int id = resumeService.save(resume);
        return new ResponseEntity<>(Response.builder().message("Резюме id=" + id + " успешно сохраненно")
                .timestamp(System.currentTimeMillis()).build(), HttpStatus.CREATED);
    }

    /**
     * Обрабатывает GET-запросы для получения списка резюме по указанной категории
     * @param category категория, по которой необходимо получить резюме
     * @return список резюме, соответствующих указанной категории
     * @throws ResponseStatusException если резюме для указанной категории не найдены
     */
    @GetMapping("/{category}")
    public List<Resume> getResumeByCategory(@PathVariable String category) {
        if (resumeService.getByCategory(Category.valueOf(category)).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return resumeService.getByCategory(Category.valueOf(category));
    }

    /**
     * Обрабатывает GET-запросы для получения резюме по его идентификатору
     * @param id идентификатор резюме, которое требуется получить
     * @return резюме с указанным идентификатором
     * @throws ResponseStatusException если резюме с указанным id не найдено
     */
    @GetMapping()
    public Resume getResumeById(@RequestParam("id") int id) {
        if (resumeService.getById(id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return resumeService.getById(id).orElse(null);
    }

    /**
     * Обрабатывает GET-запросы для получения списка всех резюме
     * @return список всех доступных резюме
     */
    @GetMapping("/all")
    public List<Resume> getAllResume() {
       return resumeService.getAll();
    }

    /**
     * Обрабатывает POST-запросы для изменения статуса решения резюме
     * @param resume объект резюме, содержащий идентификатор и новый статус решения
     * @param bindingResult объект, содержащий результаты валидации резюме
     * @return объект ResponseEntity с сообщением об успешном изменении статуса резюме и HTTP статусом 200
     * @throws ResponseStatusException если резюме с указанным id не найдено
     * @throws Exception если валидация резюме не прошла
     */
    @PostMapping("/decide")
    public ResponseEntity<Response> changeDecideResume(@RequestBody Resume resume, BindingResult bindingResult) throws Exception {
        if (resumeService.getById(resume.getId()).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }
        resumeService.changeDecide(resume.getId(), Decide.valueOf(resume.getDecide().name()));
        return new ResponseEntity<>(Response.builder().message("Резюме успешно сохраненно")
                .timestamp(System.currentTimeMillis()).build(), HttpStatus.OK);
    }

    /**
     * Обрабатывает исключения, возникающие в приложении
     * @param ex исключение, которое было выброшено
     * @return объект ResponseEntity с сообщением об ошибке и статусом HTTP 400
     */
    @ExceptionHandler
    public ResponseEntity<Response> handlerException(Exception ex) {
//        log.error(ex.getMessage(), ex);
       return new ResponseEntity<>(Response.builder().message(ex.getMessage()).timestamp(System.currentTimeMillis()).build(),HttpStatus.BAD_REQUEST) ;
    }
}
