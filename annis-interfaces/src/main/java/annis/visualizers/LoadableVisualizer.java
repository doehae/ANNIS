/*
 * Copyright 2012 SFB 632.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package annis.visualizers;

import java.io.Serializable;

/**
 * A visualizer that can callback their owners when they are loaded.
 *
 * @author Thomas Krause {@literal <krauseto@hu-berlin.de>}
 */
public interface LoadableVisualizer
{
  public void addOnLoadCallBack(Callback callback);

  public void clearCallbacks();

  /** Returns if the visualizer is ready. 
   *
   * @return True if loaded.
   */
  public boolean isLoaded();

  public interface Callback extends Serializable
  {
    public void visualizerLoaded(LoadableVisualizer origin);
  }
}
