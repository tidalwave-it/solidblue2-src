/*
 * #%L
 * *********************************************************************************************************************
 *
 * SolidBlue - open source safe data
 * http://solidblue.tidalwave.it - hg clone https://bitbucket.org/tidalwave/solidblue2-src
 * %%
 * Copyright (C) 2015 - 2015 Tidalwave s.a.s. (http://tidalwave.it)
 * %%
 * *********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * *********************************************************************************************************************
 *
 * $Id: Main.java,v b4f706516290 2015/11/07 08:47:17 fabrizio $
 *
 * *********************************************************************************************************************
 * #L%
 */
package it.tidalwave.integritychecker2.persistence.impl.mybatis;

import it.tidalwave.integritychecker2.FileAndFingerprint;
import it.tidalwave.integritychecker2.persistence.PersistentFileScan;
import it.tidalwave.util.Id;
import java.nio.file.Paths;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import static lombok.AccessLevel.PACKAGE;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici <Fabrizio dot Giudici at tidalwave dot it>
 * @version $Id: Class.java,v 631568052e17 2013/02/19 15:45:02 fabrizio $
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor(access = PACKAGE)
@EqualsAndHashCode(exclude = "transactionManager")
@ToString(exclude = "transactionManager")
public class MBPersistentFileScan implements PersistentFileScan
  {
    private TransactionManager transactionManager;

//    private final MBPersistentScan scan; FIXME

    private final Id id;

    private final Id scanId;

    private final String fileName;

    private final String fingerprint;

    MBPersistentFileScan (final TransactionManager transactionManager,
                          final MBPersistentScan scan,
                          final Id id,
                          final String fileName,
                          final String fingerprint)
      {
        this(id, scan.getId() /* FIXME */, fileName, fingerprint);
        this.transactionManager = transactionManager;
      }

    void insert()
      {
        transactionManager.runTransationally(session ->
          {
            session.getMapper(MBPersistentFileScanMapper.class).insert(this);
            return null;
          });
      }

    @Override
    public FileAndFingerprint toFileAndFingerprint()
      {
        return new FileAndFingerprint(Paths.get(fileName), fingerprint);
      }
  }
