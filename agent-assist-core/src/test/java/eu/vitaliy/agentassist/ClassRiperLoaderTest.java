package eu.vitaliy.agentassist;

import org.testng.annotations.Test;
import java.util.Collection;
import static org.assertj.core.api.Assertions.assertThat;

public class ClassRiperLoaderTest {

    @Test
    public void classRipperTestImplTest() {
        //when
        Collection<ClassRipper> rippers = new ClassRipperLoader().loadRippers();

        //when
        assertThat(rippers).hasSize(1);
        assertThat(rippers).hasAtLeastOneElementOfType(ClassRipperTestImpl.class);
    }
}
