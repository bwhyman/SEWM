package test.com.example10;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.se.working.controller.ControllerMapping.AdminSettingRequestMapping;
import com.se.working.service.AdminService;
import com.se.working.service.UserService;





/**
 * 默认事务回滚
 * 
 * @author BO
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:WebContent/WEB-INF/applicationContext.xml")
@WebAppConfiguration("WebContent") // web项目的根目录，默认为 file:src/main/webapp
@Transactional
public class MyTest {
	private static Logger logger = LogManager.getLogger(MyTest.class);
	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService userService;
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;
	private MockHttpSession session;

	@Before
	public void setSession() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		RequestBuilder builder = MockMvcRequestBuilders.post("/login").param("employeeNumber", "1007")
				.param("password", "1007");
		// 执行
		ResultActions resultActions = mockMvc.perform(builder).andDo(MockMvcResultHandlers.print());
		// MockMvcResultHandlers提供结果处理功能
		// MockMvcResultMatchers提供断言功能
		// .andExpect(MockMvcResultMatchers.status().isOk()
		
		MvcResult result = resultActions.andReturn();
		session = (MockHttpSession) result.getRequest().getSession();
		logger.info("login");
	}

	@Test
	public void test() throws Exception {
		
		RequestBuilder builder = MockMvcRequestBuilders.get(AdminSettingRequestMapping.ADD_USER).session(session);
		ResultActions resultActions = mockMvc.perform(builder).andDo(MockMvcResultHandlers.print());
	}
}
