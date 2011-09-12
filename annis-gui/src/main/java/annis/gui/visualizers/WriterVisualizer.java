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
package annis.gui.visualizers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implentation of Visualizer which uses Writers instead of OutputStream
 * @author thomas
 */
public abstract class WriterVisualizer extends Visualizer
{

  /**
   * Will create a Writer of the outstream.
   * @param outstream
   */
  @Override
  public void writeOutput(VisualizerInput input, OutputStream outstream)
  {
    try
    {
      OutputStreamWriter writer = new OutputStreamWriter(outstream, getCharacterEncoding());
      writeOutput(input, writer);
      writer.flush();
    }
    catch(IOException ex)
    {
      Logger.getLogger(WriterVisualizer.class.getName())
        .log(Level.SEVERE, "Exception when writing visualizer output.", ex);
      ex.printStackTrace(new PrintWriter(outstream));
    }
  }

  /**
   * Writes the final output to passed Wriiter. The writer should remain open.
   * @param writer
   */
  public abstract void writeOutput(VisualizerInput input, Writer writer);

}
