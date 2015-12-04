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
package it.tidalwave.integritychecker2.persistence.impl;

import it.tidalwave.integritychecker2.persistence.Persistence;
import it.tidalwave.integritychecker2.persistence.PersistentFileScan;
import it.tidalwave.integritychecker2.persistence.PersistentScan;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.inject.Inject;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/***********************************************************************************************************************
 *
 * @author  Fabrizio Giudici <Fabrizio dot Giudici at tidalwave dot it>
 * @version $Id: Class.java,v 631568052e17 2013/02/19 15:45:02 fabrizio $
 *
 **********************************************************************************************************************/
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
@Slf4j
public class DefaultPersistence implements Persistence
  {
    protected final DataSource dataSource;

    @Override
    public void shutdown()
      throws SQLException
      {
        log.info("scratch()");
        executeSQL(statement -> statement.executeUpdate("SHUTDOWN"));
      }

    @Override
    public void createTables() // FIXME: move to ScanRepository?
      throws SQLException
      {
        log.info("createTables()");
        executeSQL(statement ->
          {
            PersistentScan.createTable(statement);
            PersistentFileScan.createTable(statement);
          });
      }

    static interface Task
      {
        public void runWith (Statement statement)
          throws SQLException;
      }

    private void executeSQL (final Task task)
      throws SQLException
      {
        try (final Connection connection = dataSource.getConnection();
             final Statement statement = connection.createStatement())
          {
            task.runWith(statement);

            try
              {
                connection.commit();
              }
            catch (SQLException e)
              {
                if (!e.getMessage().startsWith("Database is already closed"))
                  {
                    throw e;
                  }
              }
          }
      }
  }