/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.documentexamination.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import rzd.vivc.documentexamination.model.dto.security.Account;
import rzd.vivc.documentexamination.model.dto.users.User;
import rzd.vivc.documentexamination.repository.AccountRepository;
import rzd.vivc.documentexamination.repository.DepartmentRepository;
import rzd.vivc.documentexamination.repository.RoleRepository;
import rzd.vivc.documentexamination.repository.UserRepository;

/**
 * Для /user
 *
 * @author VVolgina
 */
@Controller
@RequestMapping("/user")
public class UserController {

    //автоматически привязываемая реализация репозитория для пользователей

    private final UserRepository userRepository;
    //Репозиторий для отделов
    @Autowired
    private DepartmentRepository departmentRepository;
    
     @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AccountRepository accountRepository;

    /**
     * конструктор
     *
     * @param userRepository Репозиторий
     */
    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * переход к странице редактирования Для корректировки существующего
     * пользователя
     *
     * @param userID id пользователя для редактирования
     * @param model модель
     * @return view Для страницы редактирования
     */
    @RequestMapping(value = "/edit/{userID}", method = GET)
    public String edit(@PathVariable(value = "userID") long userID, Model model) {
        addDepartments(model);

        Account account = accountRepository.findWithDependencies(userID);
        model.addAttribute(account);
        model.addAttribute(account.getUser());
        return "editUser";
    }

    /**
     * переход к странице редактирования
     *
     * @param model модель
     * @return view Для страницы редактирования
     */
    @RequestMapping(value = "/edit", method = GET)
    public String edit(Model model) {
        addDepartments(model);
        Account account = new Account();
        account.setUser(new User());
        model.addAttribute(account);
        model.addAttribute(account.getUser());
        return "editUser";
    }

    /**
     * Обработка данных с формы анотация @Valid означает, что данные должны
     * соответвтвовать ограничениям в классах
     *
     * @param account аккаунт, который редактировали
     * @param errors список ошибок валидации
     * @param model модель с данными
     * @return "redirect: /users/{userID}" если все впорядке, возврат на форму,
     * если есть ошибки
     */
    @RequestMapping(value = "/edit", method = POST)
    public String processEdit(@Valid Account account, Errors errors, Model model) {
        return processEditRealisation( 0,0, account, errors, model);
    }

    /**
     * Обработка данных с формы анотация @Valid означает, что данные должны
     * соответвтвовать ограничениям в классе
     *
     * @param userID id пользоватешля
     * @param account пользователь, который редактировали
     * @param errors список ошибок валидации
     * @param model модель с данными
     * @return "redirect: /users/{userID}" если все впорядке, возврат на
     * форму, если есть ошибки
     */
    @RequestMapping(value = "/edit/{userID}", method = POST)
    public String processEdit(@PathVariable(value = "userID") long userID, @Valid Account account, Errors errors, Model model) {
        long accountID = account.getId();
        
        return processEditRealisation(userID, accountID, account, errors, model);
    }

    private String processEditRealisation(long userID, long accountID, Account account, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "editUser";
        }
        account.getUser().setId(userID);
        User save = userRepository.save(account.getUser());
        Account saved = accountRepository.save(account);
        model.addAttribute("userID", save.getId());
        return "redirect:/users/{userID}";
    }

    /**
     * ДОбавляет в модель список Отделов и ролей
     */
    private void addDepartments(Model model) {
        model.addAttribute("departments", departmentRepository.findAll(new Sort(Sort.Direction.ASC, "name")));
         model.addAttribute("roles", roleRepository.findAll(new Sort(Sort.Direction.ASC, "name")));
    }
}