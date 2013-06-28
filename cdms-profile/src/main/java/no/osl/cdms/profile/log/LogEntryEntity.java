package no.osl.cdms.profile.log;


import javax.persistence.*;

@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE")
@Table(name="CDM_LOG_ENTRY")
public abstract class LogEntryEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column(name = "LOG_ENTRY_ID")
    private long logEntryId;
}
