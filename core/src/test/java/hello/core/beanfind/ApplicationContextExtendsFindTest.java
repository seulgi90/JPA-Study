package hello.core.beanfind;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import hello.core.beanfind.ApplicationContextSameBeanFindTest.SameBeanConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ApplicationContextExtendsFindTest {
  
  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);
  
  // 부모 타입으로 조회하면, 자식 타입도 함꼐 조회한다
  // => 모든 자바 객체의 최고 부모인 object타입으로 조회하면, 모든 스프링 빈을 조회한다
  
  @Test
  @DisplayName("부모 타입으로 조회 시, 자식이 둘 이상 있으면, 중복 오류 발생")
  void findBeanByParentTypeDuplication() {
    // DiscountPolicy bean = ac.getBean(DiscountPolicy.class);
    assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean(DiscountPolicy.class));
  }
  
  @Test
  @DisplayName("부모 타입으로 조회 시, 자식이 둘 이상 있으면, 빈 이름을 지정하면 된다")
  void findBeanByParentTypeBeanName() {
     DiscountPolicy bean = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
    assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
  }
  
  @Test
  @DisplayName("특정 하위 타입으로 조회")
  void findBeanBySubTyPe() {
    RateDiscountPolicy bean = ac.getBean(RateDiscountPolicy.class);
    assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
  }
  
  @Test
  @DisplayName("부모 타입으로 모두 조회")
  void findBeanByParentTyPe() {
    Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
    assertThat(beansOfType.size()).isEqualTo(2);
    for (String key : beansOfType.keySet()) {
      System.out.println("key = " + key + "value = " + beansOfType.get(key));
    }
  }
  
    @Test
    @DisplayName("부모 타입으로 모두 조회 - Object")
    void findAllBeanByObjectType() {
      Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
      
      for (String key : beansOfType.keySet()) {
        System.out.println("key = " + key + "value = " + beansOfType.get(key));
    }
  
  }
  
  @Configuration
  static class TestConfig {
    @Bean
    public DiscountPolicy rateDiscountPolicy() {
     return new RateDiscountPolicy();
    }
    
    @Bean
    public DiscountPolicy fixDiscountPolicy() {
      return new FixDiscountPolicy();
    }
  }
  
}
