/*
 * #%L
 * *********************************************************************************************************************
 *
 * SolidBlue - open source safe data
 * http://solidblue.tidalwave.it - hg clone https://bitbucket.org/tidalwave/solidblue2-src
 * %%
 * Copyright (C) 2015 - 2019 Tidalwave s.a.s. (http://tidalwave.it)
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
 * $Id: ProgressTracker.java,v 91dd9dc0d25a 2015/11/03 20:25:03 fabrizio $
 *
 * *********************************************************************************************************************
 * #L%
 */
package it.tidalwave.integritychecker2;

import java.nio.file.Path;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici <Fabrizio dot Giudici at tidalwave dot it>
 * @version $Id: Class.java,v 631568052e17 2013/02/19 15:45:02 fabrizio $
 *
 **********************************************************************************************************************/
public interface ProgressTracker extends AutoCloseable
  {
    /*******************************************************************************************************************
     *
     * Logs the current progress.
     *
     ******************************************************************************************************************/
    public void logProgress();

    /*******************************************************************************************************************
     *
     * Updates the statistics for a freshly discovered file.
     *
     * @param   file            the file
     *
     ******************************************************************************************************************/
    public void notifyDiscoveredFile (Path file);

    /*******************************************************************************************************************
     *
     * Updates the statistics for a freshly scanned file.
     *
     * @param   file            the file
     *
     ******************************************************************************************************************/
    public void notifyScannedFile (FileAndFingerprint faf);
  }
