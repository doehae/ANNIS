package annis;

import java.util.List;
import java.util.Objects;

import org.corpus_tools.salt.common.SDocumentGraph;
import org.corpus_tools.salt.common.STextualDS;
import org.corpus_tools.salt.util.SaltUtil;
import org.eclipse.emf.common.util.URI;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimelineReconstructorTest
{
  
  private final static Logger log = LoggerFactory.getLogger(TimelineReconstructorTest.class);

  @Before
  public void setUp() throws Exception
  {
  }

  /**
   * Tests a sample dialog reconstruction.
   * The dialog is this one: https://korpling.org/annis3/?id=44b60a56-31da-4469-b438-62fdb67f28f1
   */
  @Test
  public void testBematacDialog()
  {
    SDocumentGraph docGraph = SaltUtil.loadDocumentGraph(URI.createURI(getClass().getResource("SampleDialog.salt").toString()));
    
    TimelineReconstructor.removeVirtualTokenization(docGraph);

    // instructor_dipl, instructor_norm, instructee_dipl, instructee_norm, instructee_extra, break
    List<STextualDS> texts = docGraph.getTextualDSs();
    Assert.assertEquals(6, texts.size());
    
    STextualDS instructorDipl = findTextualDSByName("instructor_dipl", texts);
    Assert.assertNotNull(instructorDipl);
    Assert.assertEquals("in Richtung des Toasters gehst ja gehst", instructorDipl.getText());
    
  }
  
  private STextualDS findTextualDSByName(String name, List<STextualDS> texts)
  {
    if(texts != null)
    {
      for(STextualDS t : texts)
      {
        if(Objects.equals(name, t.getName()))
        {
          return t;
        }
      }
    }
    
    return null;
  }

}
