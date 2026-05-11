@Aspect
@Component
public class AuditAspect {

    @Autowired
    private AuditLogRepository repo;

    @After("execution(* com.internship.tool.controller.*.*(..))")
    public void logActivity(JoinPoint jp) {

        System.out.println("AOP IS RUNNING");

        AuditLog log = new AuditLog();
        log.setAction(jp.getSignature().getName());
        log.setDetails(jp.toString());
        log.setTimestamp(LocalDateTime.now());

        repo.save(log);
    }
}