/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.documentexamination.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 * @author VVolgina
 */
@Configuration
@Import({SpringDateConfigMySQL.class, WebSecurityConfig.class})
@ComponentScan(basePackages = {"rzd.vivc.documentexamination.service", "rzd.vivc.documentexamination.form.service"})
public class RootConfig {
}
