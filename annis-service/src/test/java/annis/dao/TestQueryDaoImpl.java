/*
 * Copyright 2009-2011 Collaborative Research Centre SFB 632
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package annis.dao;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import annis.AnnisXmlContextLoader;
import annis.ql.parser.AnnisParserAntlr;
import annis.ql.parser.QueryData;
import annis.service.objects.AnnisCorpus;
import annis.service.objects.DocumentBrowserConfig;
import annis.sqlgen.ListCorpusAnnotationsSqlHelper;
import annis.sqlgen.ListCorpusSqlHelper;
import annis.test.TestHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{
  "file:src/main/distribution/conf/spring/Common.xml",
  "file:src/main/distribution/conf/spring/Dao.xml"
}, loader=AnnisXmlContextLoader.class)
public class TestQueryDaoImpl
{

  @Resource(name="queryDao")
  private QueryDao queryDaoBean;

  // simple SpringDao instance with mocked dependencies
  private QueryDaoImpl queryDao;
  @Mock
  private AnnisParserAntlr annisParser;
  @Mock
  private JdbcTemplate jdbcTemplate;
  @Mock
  private ListCorpusSqlHelper listCorpusHelper;
  @Mock
  private ListCorpusAnnotationsSqlHelper listCorpusAnnotationsHelper;
  
  // constants for flow control verification
  private static final QueryData PARSE_RESULT = new QueryData();
  private static final String SQL = "SQL";
  private static final List<Long> CORPUS_LIST = new ArrayList<>();

  @SuppressWarnings("unchecked")
  @Before
  public void setup()
  {
    initMocks(this);
       
    queryDao = new QueryDaoImpl();
    queryDao.setAqlParser(annisParser);
    queryDao.setListCorpusSqlHelper(listCorpusHelper);
    queryDao.setListCorpusAnnotationsSqlHelper(listCorpusAnnotationsHelper);
    
    queryDao.setJdbcTemplate(jdbcTemplate);
    verify(jdbcTemplate).getDataSource();
    
    when(annisParser.parse(anyString(), anyList())).thenReturn(PARSE_RESULT);
    
  }

  // check dependencies
  @Test
  public void springManagedInstanceHasAllDependencies()
  {

    QueryDaoImpl springManagedDao = (QueryDaoImpl) TestHelper.proxyTarget(queryDaoBean);
    assertThat(springManagedDao.getJdbcTemplate(), is(not(nullValue())));
    assertThat(springManagedDao.getAqlParser(), is(not(nullValue())));
    assertThat(springManagedDao.getListCorpusSqlHelper(), is(not(nullValue())));
    assertThat(springManagedDao.getListCorpusAnnotationsSqlHelper(),
      is(not(nullValue())));

    assertThat(springManagedDao.getSqlSessionModifiers(), is(not(nullValue())));
    assertThat(springManagedDao.getListCorpusByNameDaoHelper(), is(
      not(nullValue())));

  }



  @SuppressWarnings("unchecked")
  @Ignore
  public void listCorpusByName()
  {
    final List<String> CORPUS_NAMES = mock(List.class);

    ListCorpusByNameDaoHelper listCorpusByNameDaoHelper =
      mock(ListCorpusByNameDaoHelper.class);
    queryDao.setListCorpusByNameDaoHelper(listCorpusByNameDaoHelper);
    when(listCorpusByNameDaoHelper.createSql(anyList())).thenReturn(SQL);

//		String sql = "SELECT id FROM corpus WHERE name IN (?) AND top_level = 't'";

    // must use expected values here, otherwise the verify below breaks because it is operating on a spy
    List<Long> wtf = jdbcTemplate.query(anyString(),
      any(ListCorpusByNameDaoHelper.class));
    when(wtf).thenReturn(CORPUS_LIST);

    assertThat(queryDao.mapCorpusNamesToIds(CORPUS_NAMES), is(CORPUS_LIST));

    verify(listCorpusByNameDaoHelper).createSql(CORPUS_NAMES);
    verify(jdbcTemplate).query(SQL, listCorpusByNameDaoHelper);
  }

  @Test
  public void sessionTimeout()
  {
    // time out after 100 seconds
    int timeout = 100;
    queryDao.setTimeout(timeout);

    // call (query data not needed)
    queryDao.modifySqlSession(jdbcTemplate, null);

    // verify correct session timeout
    verify(jdbcTemplate).update("SET statement_timeout TO " + timeout);
  }

  @Test
  public void noTimeout()
  {
    // 0 indicates no timeout
    queryDao.setTimeout(0);
    
    // call
    queryDao.modifySqlSession(jdbcTemplate, null);

    // verify that nothing has happened
    verifyNoMoreInteractions(jdbcTemplate);
  }

  /**
   * Tests only an invalid corpus id.
   */
  @Test
  public void mapCorpusIdsToNames()
  {
    long invalidCorpusId = -1;
    List<Long> ids = new ArrayList<>();
    ids.add(invalidCorpusId);
    List<String> names = queryDao.mapCorpusIdsToNames(ids);

    Assert.assertTrue("list of names must be empty: ", names.isEmpty());
  }

  /**
   * Tests only an invalid corpus id.
   */
  @Test(expected = DataAccessException.class)
  public void mapCorpusIdToName()
  {
    long invalidCorpusId = -1;
    List<Long> ids = new ArrayList<>();
    ids.add(invalidCorpusId);
    when(queryDao.mapCorpusIdsToNames(ids)).thenReturn(new ArrayList<String>());
    queryDao.mapCorpusIdToName(invalidCorpusId);
  }

  @Test
  public void getDefaultDocBrowserConfiguration()
  {
    DocumentBrowserConfig docBrowseConfig =
      queryDao.getDefaultDocBrowserConfiguration();

    Assert.assertNotNull("default document browser config may not be null", docBrowseConfig);
    Assert.assertNotNull(docBrowseConfig.getVisualizers());
    Assert.assertTrue(docBrowseConfig.getVisualizers().length > 0);
    Assert.assertTrue(docBrowseConfig.getVisualizers()[0].getType() != null);
    Assert.assertTrue(docBrowseConfig.getVisualizers()[0].getDisplayName() != null);
  }
}
