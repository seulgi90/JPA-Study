package hello.core.beanfind;

import hello.core.AppConfig;
import java.lang.reflect.AnnotatedArrayType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration.AnnotationConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class ApplicationContextInfoTest {
  
  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
  
  @Test
  @DisplayName("모든 빈 출력하기")
  void findAllBean() {
    //  ac.getBeanDefinitionNames() : 스프링에 등록된 모든 빈 이름을 조회
    String[] beanDefinitionNames = ac.getBeanDefinitionNames();
    
    for(String beanDefinitionName : beanDefinitionNames) {
      Object bean = ac.getBean(beanDefinitionName);
      System.out.println("name = " + beanDefinitionNames + "Object =" + bean);
  
    }
  }
  
  @Test
  @DisplayName("애플리케이션 빈 출력하기")
  void findApplicationBean() {
    String[] beanDefinitionNames = ac.getBeanDefinitionNames();
    
    for(String beanDefinitionName : beanDefinitionNames) {
      
      BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
      
      // ac.getBean() :  빈 조회
      // ROLE_APPLICATION : 내가 등록한 빈 출력
      // ROLE_INFRASTRUCTURE :  스프링이 내부에서 사용하는 빈
      if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
        Object bean = ac.getBean(beanDefinitionName);
        System.out.println("name = " + beanDefinitionNames + "Object =" + bean);
      }
  
    }
  }
  
}
