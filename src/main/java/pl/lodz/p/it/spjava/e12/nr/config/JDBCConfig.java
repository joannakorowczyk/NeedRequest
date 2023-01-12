package pl.lodz.p.it.spjava.e12.nr.config;

import java.sql.Connection;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@DataSourceDefinition( // Pula połączeń z domyślnym poziomem izolacji transakcji ReadCommitted
        name = "java:app/jdbc/NeedRequestDS",
        className = "org.apache.derby.jdbc.ClientDataSource",
        serverName = "localhost",
        portNumber = 1527,
        databaseName = "NeedRequestDB",
        user = "need",
        password = "need123",
        isolationLevel = Connection.TRANSACTION_READ_COMMITTED)

@Stateless
public class JDBCConfig {

//    Uczynienie z tej klasy komponentu Stateless i wstrzykniecie zarzadcy encji korzystajacego z NeedRequestPU
//    powoduje aktywowanie tej jednostki skladowania, a w konsekwencji utworzenie (z ew. usunieciem!) struktur w bazie danych
//    @see persistence.xml
    @PersistenceContext(unitName = "NeedRequestPU")
    private EntityManager em;
}
