package com.lynda.common.config;

import com.lynda.common.data.repository.CustomerRepository;
import com.lynda.common.data.repository.InventoryItemRepository;
import com.lynda.common.data.repository.SalesOrderRepository;
import com.lynda.common.service.InventoryService;
import com.lynda.common.service.OrderService;
import com.lynda.common.service.impl.InventoryServiceImpl;
import com.lynda.common.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

@Configuration
@Import(DataConfig.class)
@PropertySource("classpath:/application-${spring.profiles.active}.properties")
public class AppConfig {

    @Value("${greeting.text}")
    private String greetingText;

    @Value("${greeting.preamble}")
    private String getGreetingPreamble;

    @Value("#{new Boolean(environment['spring.profiles.active'] == 'dev')}")
    private boolean isDev;

    public class Worker{
        private String preamble;
        private String text;

        public Worker(String preamble, String text){
            this.preamble = preamble;
            this.text = text;
            System.out.println("New instance");
        }

        public void execute(){
            System.out.println(preamble + " " + text + " is dev: " + isDev);

        }

    }

    @Bean
    @Scope("prototype")
    public Worker worker(){
        return new Worker(getGreetingPreamble, greetingText);
    }

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Bean
    public OrderService orderService(InventoryService inventoryService, CustomerRepository customerRepository, SalesOrderRepository salesOrderRepository){
        return new OrderServiceImpl(inventoryService, customerRepository, salesOrderRepository);
    }

    @Bean
    public InventoryService inventoryService(InventoryItemRepository inventoryItemRepository){
        return new InventoryServiceImpl(inventoryItemRepository);
    }


    public static void main (String[] args){
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        OrderService orderService = context.getBean(OrderService.class);
        System.out.println(orderService==null?"NULL":"A OK");
        Worker worker = context.getBean(Worker.class);
        worker.execute();

        Worker worker1 = context.getBean(Worker.class);
        worker1.execute();
    }
}

