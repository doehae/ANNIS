/*
 * Copyright 2013 SFB 632.
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
package annis.gui.widgets.gwt.client.ui.autoheightiframe;

import com.vaadin.shared.AbstractComponentState;

/**
 *
 * @author Thomas Krause {@literal <krauseto@hu-berlin.de>}
 */
public class AutoHeightIFrameState extends AbstractComponentState
{
  String url;
  int additionalHeight = -1;

  public String getUrl()
  {
    return url;
  }

  public void setUrl(String url)
  {
    this.url = url;
  }

  public int getAdditionalHeight()
  {
    return additionalHeight;
  }

  public void setAdditionalHeight(int additionalHeight)
  {
    this.additionalHeight = additionalHeight;
  }
  
}
