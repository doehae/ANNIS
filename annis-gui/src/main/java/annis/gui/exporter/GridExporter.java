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
package annis.gui.exporter;

import java.io.IOException;

import annis.model.AnnisNode;
import annis.model.Annotation;
import annis.service.ifaces.AnnisResult;
import annis.service.ifaces.AnnisResultSet;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class GridExporter extends GeneralTextExporter
{

  @Override
  public void convertText(AnnisResultSet queryResult, LinkedList<String> keys, 
    Map<String,String> args, Writer out, int offset) throws IOException
  {


    boolean showNumbers = true;
    if (args.containsKey("numbers"))
    {
      String arg = args.get("numbers");
      if (arg.equalsIgnoreCase("false")
        || arg.equalsIgnoreCase("0")
        || arg.equalsIgnoreCase("off"))
      {
        showNumbers = false;
      }
    }

    int counter = 0;
    for (AnnisResult annisResult : queryResult)
    {
      HashMap<String, TreeMap<Long, Span>> annos =
        new HashMap<String, TreeMap<Long, Span>>();

      counter++;
      out.append((counter + offset) + ". ");

      long tokenOffset = annisResult.getGraph().getTokens().get(0).getTokenIndex() - 1;
      for (AnnisNode resolveNode : annisResult.getGraph().getNodes())
      {

        for (Annotation resolveAnnotation : resolveNode.getNodeAnnotations())
        {
          String k = resolveAnnotation.getName();
          if (annos.get(k) == null)
          {
            annos.put(k, new TreeMap<Long, Span>());
          }

          // create a separate span for every annotation
          annos.get(k).put(resolveNode.getLeftToken(), new Span(resolveNode.getLeftToken(), resolveNode.getRightToken(),
            resolveAnnotation.getValue()));
          
        }
      }

      for (String k : keys)
      {

        if ("tok".equals(k))
        {
          out.append("\t " + k + "\t ");
          for (AnnisNode annisNode : annisResult.getGraph().getTokens())
          {
            out.append(annisNode.getSpannedText() + " ");
          }
          out.append("\n");
        }
        else
        {
          if(annos.get(k) != null)
          {
            out.append("\t " + k + "\t ");
            for(Span s : annos.get(k).values())
            {

              out.append(s.getValue());

              if (showNumbers)
              {
                long leftIndex = Math.max(1, s.getStart() - tokenOffset);
                long rightIndex = s.getEnd() - tokenOffset;
                out.append("[" + leftIndex
                  + "-" + rightIndex + "]");
              }
              out.append(" ");

            }
            out.append("\n");
          }
        }
      }

      out.append("\n\n");
    }
  }


  private class Span
  {

    private long start;
    private long end;
    private String value;

    public Span(long start, long end, String value)
    {
      this.start = start;
      this.end = end;
      this.value = value;
    }

    public long getStart()
    {
      return start;
    }

    public void setStart(long start)
    {
      this.start = start;
    }

    public long getEnd()
    {
      return end;
    }

    public void setEnd(long end)
    {
      this.end = end;
    }

    public String getValue()
    {
      return value;
    }

    public void setValue(String value)
    {
      this.value = value;
    }
  }
}
