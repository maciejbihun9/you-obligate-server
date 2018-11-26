package com.maciejbihun.controller;

import com.maciejbihun.Application;
import com.maciejbihun.HibernateConf;
import com.maciejbihun.dto.BondDto;
import com.maciejbihun.models.Bond;
import com.maciejbihun.models.UserAccountInObligationGroup;
import com.maciejbihun.repository.BondRepository;
import com.maciejbihun.repository.UserAccountInObligationGroupRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes = {Application.class, HibernateConf.class})
// @WebMvcTest(UserRegisteredServiceController.class)
@ActiveProfiles("test")
public class BondServiceIntegrationTest {

    @Autowired
    private BondController bondController;

    @Autowired
    private BondRepository bondRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserAccountInObligationGroupRepository userAccountInObligationGroupRepository;

    @Autowired
    private DataSource testDataSource;

    @Autowired
    private UserAccountInObligationGroupController userAccountInObligationGroupController;

    @Before
    public void initData(){
        // init test database with the data from init script
        /*Resource initData = new ClassPathResource("scripts/data-h2.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initData);
        DatabasePopulatorUtils.execute(databasePopulator, testDataSource);*/
    }

    //@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data-h2.sql")
    @Test
    public void savingObligationGroupAccountCausesBondSaving(){
        // given
        Long bondId = 1L;
        Long obligationStrategyId = 1L;
        Long obligationGroupAccountId = 1L;
        Integer amountOfUnitsToPay = 100;
        BondDto bondDto = new BondDto(amountOfUnitsToPay, obligationStrategyId, obligationGroupAccountId);

        // when
        ResponseEntity<Bond> bondInObligationGroupResponseEntity = bondController.createBondInObligationGroup(bondDto);

        ResponseEntity<UserAccountInObligationGroup> userAccountInObligationGroupWithBonds =
                userAccountInObligationGroupController.getUserAccountInObligationGroupWithBonds(obligationGroupAccountId);

        Bond savedBond = userAccountInObligationGroupWithBonds.getBody().getBonds().get(0);

        Bond bondById = bondRepository.findById(bondId).get();
        List<Bond> allBonds = bondRepository.findAll();

        // then
        assertNotNull(savedBond);
        assertEquals(Integer.valueOf(100), savedBond.getAmountOfUnitsToPay());
        assertEquals(Long.valueOf(1), bondInObligationGroupResponseEntity.getBody().getId());
        assertEquals(Integer.valueOf(100), bondById.getAmountOfUnitsToPay());
        assertEquals(1, allBonds.size());
    }


}
