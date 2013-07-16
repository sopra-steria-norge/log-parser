package no.osl.cdms.profile.log;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static junit.framework.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:test-cdms-profile-ctx.xml",
        "classpath:test-cdms-profile-infra-ctx.xml"})
@Transactional
public class LogRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private LogRepository logRepository;

    private ProcedureEntity procedureEntity1, procedureEntity2;
    private TimeMeasurementEntity timeMeasurementEntity1, timeMeasurementEntity2, timeMeasurementEntity3;
    private MultiContextEntity multiContextEntity1, multiContextEntity2;
    private LayoutEntity layoutEntity1, layoutEntity2;

    @Before
    public void before() {

        procedureEntity1 = new ProcedureEntity("testName1", "testClass1", "testMethod1");
        procedureEntity2 = new ProcedureEntity("testName2", "testClass1", "testMethod2");

        multiContextEntity1 = new MultiContextEntity(new DateTime("2013-06-25T01:15:52.458Z").toDate(),
                new DateTime("2013-06-25T01:15:52.700Z").toDate());
        multiContextEntity2 = new MultiContextEntity(new DateTime("2011-06-25T01:15:52.458Z").toDate(),
                new DateTime("2011-06-25T01:15:52.700Z").toDate());

        timeMeasurementEntity1 = new TimeMeasurementEntity(procedureEntity1, multiContextEntity1,
                new DateTime("2013-06-25T01:15:52.458Z").toDate(), "PT0.017S");
        timeMeasurementEntity2 = new TimeMeasurementEntity(procedureEntity2, multiContextEntity1,
                new DateTime("2013-06-25T01:15:52.458Z").toDate(), "PT0.107S");
        timeMeasurementEntity3 = new TimeMeasurementEntity(procedureEntity1, multiContextEntity2,
                new DateTime("2007-06-25T01:15:52.458Z").toDate(), "PT0.107S");

        layoutEntity1 = new LayoutEntity("Layout 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum at aliquet nisl. Proin aliquet, augue nec sodales pulvinar, lacus felis vestibulum dolor, non porta leo nisl adipiscing neque. Etiam nulla sem, interdum quis nibh quis, facilisis porttitor urna. Nulla a rhoncus diam, id faucibus quam. Praesent ut nisl ullamcorper, laoreet tellus eget, lobortis enim. Aliquam blandit felis sed leo sollicitudin facilisis. Aliquam semper est ante, quis lacinia nunc viverra vitae. Nam vel eros lorem. Maecenas dapibus at nisl non laoreet. Quisque sed arcu consequat nisi vestibulum aliquam sit amet ut erat. Ut vitae blandit massa. Nulla facilisi. Sed accumsan ornare blandit. Fusce tempor eleifend orci, ac ultricies dui ultrices sed. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque sagittis felis id ante tempus adipiscing. Mauris sit amet ipsum sed mauris porta adipiscing. In gravida urna ac diam sodales, id ornare enim aliquam. Vivamus sollicitudin mauris vitae erat porttitor, et viverra leo interdum. Donec sollicitudin augue vel eleifend eleifend. Integer semper enim vitae nisi tristique, sit amet vestibulum tellus ornare. In sed mauris eu magna tristique feugiat. Sed a enim ac nibh blandit adipiscing eu at ipsum. Quisque consectetur purus sit amet orci adipiscing rhoncus. Fusce et posuere ante, vel vestibulum metus. Integer velit eros, dictum nec lectus vitae, commodo semper nibh. Aliquam a turpis sed turpis consequat dignissim eu vel turpis. Donec mattis odio nec commodo blandit. In adipiscing iaculis elit, pretium viverra est porttitor sit amet. Aliquam ut metus tristique neque ultricies volutpat sed nec lectus. Praesent a est et enim pulvinar fermentum nec sit amet nulla. Nullam ac vehicula risus, in hendrerit purus. Ut pharetra, nunc nec dictum viverra, lectus neque fringilla nisi, nec posuere nisl est vitae lorem. Mauris gravida eros at odio accumsan, vel pulvinar lorem facilisis. Nullam eleifend scelerisque arcu et tincidunt. Duis feugiat at nisl at gravida. Curabitur auctor velit ut sagittis interdum. Curabitur rutrum ipsum id nibh facilisis auctor at sed eros. Nullam sollicitudin aliquam neque nec imperdiet. Fusce id faucibus lectus. Morbi at condimentum urna. Fusce sed justo eu lacus mollis euismod. In tempus eu erat non malesuada. Etiam malesuada, nibh non dictum tempus, nunc lorem gravida lacus, tempus lobortis elit est sed massa. Nam justo sem, vehicula eget arcu in, suscipit tincidunt quam. Nunc pharetra in eros pretium tincidunt. Nunc sit amet nisi a odio feugiat pellentesque eget quis massa. In hac habitasse platea dictumst. Nullam dapibus diam tempus nisi tempor semper. Donec mollis vulputate est, in gravida mi pellentesque fringilla. Curabitur consequat accumsan mauris, vel elementum turpis consequat a. Praesent sed rutrum ipsum. Vestibulum vestibulum nisl a ante mattis, vel consequat turpis pulvinar. Suspendisse commodo, enim ac imperdiet feugiat, lacus dui vestibulum ipsum, id hendrerit lectus turpis vel velit. Suspendisse nisi arcu, placerat sed mauris in, tempor pellentesque risus. Curabitur nec rhoncus erat. Maecenas in ornare felis. Duis rhoncus felis vel consequat aliquet. Morbi interdum, lacus sed sollicitudin dictum, ipsum augue fermentum massa, eget dapibus elit est id sem. Phasellus eget tincidunt mauris. Pellentesque vitae arcu lectus. Nulla mattis urna ac gravida malesuada. Vivamus elit felis, tristique sed urna nec, fringilla suscipit metus. Duis sit amet enim ante. Morbi quis lorem at libero tempor placerat. Fusce in semper mi, id tincidunt sem. Aliquam ut accumsan leo, nec tristique leo. Quisque sapien est, commodo non erat nec, fringilla tempus eros. Vivamus rhoncus porttitor tortor porttitor scelerisque. Aenean gravida aliquam lobortis. Curabitur rhoncus pulvinar mauris, molestie sodales odio tincidunt at. Pellentesque consequat, massa non posuere vehicula, dui mauris pretium eros, in dictum orci est a dolor. Duis lacus turpis, luctus at dapibus volutpat, dignissim quis libero. In hac habitasse platea dictumst. Donec condimentum fringilla erat, at sodales lectus rhoncus sed. Maecenas sit amet magna et sem placerat feugiat nec eu leo. Nunc lorem mauris, tempus eget eleifend sed, hendrerit ut risus. Nulla viverra est justo. Vivamus egestas est lorem, eu dignissim odio hendrerit ultrices. Donec accumsan libero sed neque dapibus, eu sodales ante bibendum. Maecenas sed diam turpis. Duis elit elit, volutpat at ullamcorper non, tincidunt ut sem. Cras ultricies sem vel nisl condimentum, in semper justo pharetra. In vitae aliquam dolor, id semper augue. Nullam luctus ipsum sed tortor porttitor cursus. Nulla eu posuere dui. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Phasellus interdum dictum sem. Quisque mauris metus, sodales commodo dolor semper, aliquet congue nunc. Nam dui augue, porta sed quam et, lobortis semper leo. Ut diam orci, bibendum eget pharetra id, convallis eget eros. Donec ut erat sem. Mauris sodales ornare quam in aliquam. Phasellus interdum aliquam pulvinar. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Ut aliquam metus mi, nec tempus leo congue ac. In laoreet felis non quam rutrum, tempus tristique massa ullamcorper. Duis placerat fermentum vehicula. Aliquam turpis urna, lobortis blandit mauris et, bibendum vehicula justo. In hac habitasse platea dictumst. In mollis congue scelerisque. Maecenas varius nulla id dolor consequat, et facilisis risus ornare. Nulla facilisi. Fusce mollis semper eros molestie vestibulum. Duis commodo quam ut lacus viverra, vitae auctor justo congue. Pellentesque a laoreet eros, tincidunt rutrum quam. Pellentesque eget malesuada ipsum, quis laoreet dolor. Donec auctor non nunc rhoncus interdum. Etiam eget tortor sit amet metus ullamcorper sollicitudin sed eget magna. Sed a leo iaculis, egestas nisi id, gravida lacus. Vivamus a orci ante. Mauris eget odio enim. Vestibulum pellentesque odio porta tincidunt pretium. Integer iaculis dolor sit amet augue rhoncus mollis. Nullam pharetra quam sed dolor dapibus lacinia. Phasellus consequat vehicula erat, nec ultrices elit. Sed mattis libero eros, vitae congue orci euismod in. Proin iaculis nibh vulputate, imperdiet erat et, cursus sapien. Etiam luctus luctus ultricies. Ut at neque in augue ultrices mollis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse venenatis imperdiet magna, et faucibus ligula placerat vitae. Pellentesque metus ligula, lacinia ut tincidunt et, molestie at ipsum. Quisque tincidunt massa sapien, quis congue felis auctor quis. Nulla vestibulum viverra ante in laoreet. Suspendisse potenti. Phasellus sollicitudin interdum dui ut lacinia. Nullam mi diam, fermentum at leo ac, consectetur dignissim ligula. Integer aliquam justo sed dapibus porta. Curabitur at facilisis leo. Fusce vitae lorem et velit gravida consectetur. Integer turpis nibh, pretium sed sem in, semper fringilla lacus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque eros lectus, ultrices nec luctus dictum, sollicitudin ut nunc. Ut dictum rhoncus nisl, sed venenatis lorem lobortis vel. Integer quis cursus lectus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Duis a est tempus, porta ipsum ut, fringilla quam. Mauris lacinia porta ante id rutrum. Vivamus ultricies metus et arcu vestibulum, nec ultricies lacus posuere. Nunc imperdiet gravida augue id euismod. Sed neque felis, dictum ac pretium tempor, blandit quis ipsum. Maecenas viverra, augue quis cursus mattis, erat lacus consectetur lectus, quis feugiat enim erat at nulla. Aenean sed leo elit. Suspendisse pellentesque molestie nisi. Vestibulum tincidunt ipsum eu enim posuere mollis. Nunc sit amet magna sit amet massa gravida iaculis volutpat id orci. Nulla quis cursus lorem. Pellentesque congue sodales ipsum, pulvinar blandit massa ultrices a. Nulla faucibus lobortis massa et facilisis. Nam hendrerit sapien ante, elementum consequat tortor gravida eget. Nullam ac suscipit mi. Vestibulum vel purus non nisi feugiat fringilla. Maecenas suscipit at enim in lobortis. Mauris varius diam vitae elit placerat, ut congue arcu placerat. Morbi id placerat lacus. Suspendisse varius justo vitae vehicula convallis. Morbi in odio malesuada, malesuada purus eu, pharetra quam. Cras justo metus, tincidunt ut enim id, tincidunt pellentesque metus. Aliquam mauris massa, venenatis ut semper ac, bibendum iaculis dolor. Donec elementum luctus metus quis dignissim. Donec pretium augue ut arcu ultrices auctor. Ut congue ipsum volutpat, scelerisque nibh at, volutpat erat. Nulla eleifend sapien mauris, vel adipiscing augue laoreet at. Sed varius augue at sodales pharetra. Mauris non iaculis turpis, et ornare tellus. Curabitur congue libero ut dui imperdiet faucibus. Etiam faucibus consequat fringilla. Aliquam erat volutpat. Ut luctus ultrices risus adipiscing euismod. Aliquam erat volutpat. Curabitur eu tellus elit. Quisque et nibh accumsan, cursus mauris id, venenatis magna. Integer vulputate posuere sodales. In sed mi eget lorem mattis accumsan. Praesent vel mi vestibulum, tincidunt lorem vel, adipiscing justo. Donec ut elit pharetra, tincidunt odio tempor, lobortis libero. In in neque in leo eleifend venenatis. Fusce lobortis nec tortor malesuada mollis. Morbi convallis lobortis velit non tincidunt. Lorem ipsum dolor sit amet, consectetur adipiscing elit. In at elit sapien. Sed ac ipsum nulla. Phasellus placerat vestibulum orci. Etiam eget tellus convallis, euismod sem sit amet, pharetra tellus. Vivamus varius velit nibh, ut pharetra dolor ornare vehicula. Donec dictum sodales sem, sit amet gravida nibh porta quis. Nam posuere orci at mauris iaculis, sed molestie ipsum ultrices. Sed tristique non ante non porttitor. Donec elit enim, tristique vel tempor sed, molestie pulvinar tellus. Maecenas sodales viverra quam, eget dapibus est feugiat eget. Sed sed posuere diam. Quisque eget mi posuere, mattis purus vitae, varius quam amet.");
        layoutEntity2 = new LayoutEntity("Layout 2", "layout 2 sin JSON");

        entityManager.persist(timeMeasurementEntity1);
        entityManager.persist(timeMeasurementEntity2);
        entityManager.persist(timeMeasurementEntity3);
        entityManager.persist(layoutEntity1);
        entityManager.persist(layoutEntity2);
    }

    @Test
    public void testLayoutEntity() {
        boolean l1=false, l2=false;
        List<LayoutEntity> layoutEntities = logRepository.getAllLayoutEntities();
        for (LayoutEntity layoutEntity: layoutEntities) {
            if (layoutEntity.equals(layoutEntity1)) l1 = true;
            if (layoutEntity.equals(layoutEntity2)) l2 = true;
        }
        assertTrue(l1);
        assertTrue(l2);

        LayoutEntity fromDatabase = logRepository.getLayoutEntity(layoutEntity1.getId());
        assertTrue(fromDatabase.equals(layoutEntity1));
        assertFalse(fromDatabase.equals(layoutEntity2));

        fromDatabase = logRepository.getLayoutEntity(layoutEntity2.getId());
        assertTrue(fromDatabase.equals(layoutEntity2));
        assertFalse(fromDatabase.equals(layoutEntity1));
    }

    @Test
    public void persistNewProcedure_test() {
        System.out.println("persistNewProcedure_test");
        ProcedureEntity procedureEntity = new ProcedureEntity("testName", "testClass1", "testMethod1");
        logRepository.persistNewProcedure(procedureEntity);
        assertEquals(procedureEntity, entityManager.find(ProcedureEntity.class, procedureEntity.getId()));
    }

    @Test
    public void persistNewMultiContext_test() {
        System.out.println("persistNewMultiContext_test");
        TimeMeasurementEntity tme = new TimeMeasurementEntity(procedureEntity1, multiContextEntity1,
                new DateTime("2013-06-25T01:15:52.458Z").toDate(), "PT0.107S");
        MultiContextEntity multiContextEntity = new MultiContextEntity(new DateTime("2013-06-25T01:15:52.458Z").toDate(),
                new DateTime("2013-06-25T01:15:52.458Z").toDate());
        logRepository.persistNewMultiContext(multiContextEntity);
        assertEquals(multiContextEntity, entityManager.find(MultiContextEntity.class, multiContextEntity.getId()));
    }

    @Test
    public void persistNewTimeMeasurement_test() {
        System.out.println("persistNewTimeMeasurement_test");
        TimeMeasurementEntity tme = new TimeMeasurementEntity(procedureEntity1, multiContextEntity1,
                new DateTime("2013-06-25T01:15:52.458Z").toDate(), "PT0.107S");
        logRepository.persistNewTimeMeasurement(tme);
        assertEquals(tme, entityManager.find(TimeMeasurementEntity.class, tme.getId()));
    }

    @Test
    public void getProcedure_test() {
        System.out.println("getProcedure_test");
        assertEquals(logRepository.getProcedure(procedureEntity1.getId()), procedureEntity1);
        assertEquals(logRepository.getProcedure(procedureEntity2.getId()), procedureEntity2);
    }

    @Test
    public void getMultiContext_test() {
        System.out.println("getMultiContext_test");
        assertEquals(logRepository.getMultiContext(multiContextEntity1.getId()), multiContextEntity1);
    }

    @Test
    public void getTimeMeasurement_test() {
        System.out.println("getTimemeasurement_test");
        assertEquals(logRepository.getTimeMeasurement(timeMeasurementEntity1.getId()), timeMeasurementEntity1);
        assertEquals(logRepository.getTimeMeasurement(timeMeasurementEntity2.getId()), timeMeasurementEntity2);
    }

    @Test
    public void getTimeMeasurementsByProcedure_test1() {
        System.out.println("getTimeMeasurementsByProcedure_test1");
        DateTime date = new DateTime("2012-06-25T01:15:52.458Z");
        assertTrue(logRepository.getTimeMeasurementsByProcedure(date, new DateTime(), procedureEntity1).size() == 1);
        assertTrue(logRepository.getTimeMeasurementsByProcedure(date, new DateTime(), procedureEntity1).contains(timeMeasurementEntity1));
        assertFalse(logRepository.getTimeMeasurementsByProcedure(date, new DateTime(), procedureEntity1).contains(timeMeasurementEntity3));

    }

    @Test
    public void getTimeMeasurementsByProcedure_test2() {
        System.out.println("getTimeMeasurementsByProcedure_test2");
        assertTrue(logRepository.getTimeMeasurementsByProcedure(procedureEntity1).size() == 2);
        assertTrue(logRepository.getTimeMeasurementsByProcedure(procedureEntity1).contains(timeMeasurementEntity1));
        assertTrue(logRepository.getTimeMeasurementsByProcedure(procedureEntity1).contains(timeMeasurementEntity3));

    }


}
