package hermes2pro.function;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.samples.helloworld.Application;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

@SpringBootTest(classes = Application.class)
@PropertySource("classpath:application.properties")
class TestRecordParameter {

	@Test
	void testRecordParameterAddField() {
		RecordParameter rp=new RecordParameter();
		rp.addField(null, null);
	}

}
