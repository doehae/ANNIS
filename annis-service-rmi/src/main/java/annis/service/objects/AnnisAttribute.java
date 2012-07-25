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
package annis.service.objects;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AnnisAttribute implements Serializable
{

  public enum Type
  {
    node,
    edge,
    segmentation,
    unknown
  };

  public enum SubType
  {
    n,
    d,
    p,
    c,
    unknown
  };

  
  private String name = "";
  private String edgeName = null;
  private Set<String> distinctValues = new HashSet<String>();
  private Type type;
  private SubType subtype;

  
  @XmlElementWrapper(name="value-set")
  @XmlElement(name="value")
  public List<String> getValueSet()
  {
    return new LinkedList<String>(this.distinctValues);
  }
  
  public void setValueSet(List<String> values)
  {
    this.distinctValues.clear();
    this.distinctValues.addAll(values);
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getEdgeName()
  {
    return edgeName;
  }

  public void setEdgeName(String edgeName)
  {
    this.edgeName = edgeName;
  }


  public Type getType()
  {
    return type;
  }

  public void setType(Type type)
  {
    this.type = type;
  }

  public SubType getSubtype()
  {
    return subtype;
  }

  public void setSubtype(SubType subtype)
  {
    this.subtype = subtype;
  }

  public void addValue(String value)
  {
    this.distinctValues.add(value);
  }

  public boolean hasValue(String value)
  {
    return this.distinctValues.contains(value);
  }

  @Override
  public String toString()
  {
    return name + " " + distinctValues;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj == null || !(obj instanceof AnnisAttribute))
    {
      return false;
    }

    AnnisAttribute other = (AnnisAttribute) obj;

    return new EqualsBuilder().append(this.name, other.name).append(this.distinctValues, other.distinctValues).isEquals();
  }

  @Override
  public int hashCode()
  {
    return new HashCodeBuilder().append(name).append(distinctValues).toHashCode();
  }
}
