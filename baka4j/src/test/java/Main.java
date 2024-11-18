import xyz.slosa.BakalariAPI;
import xyz.slosa.endpoints.http.impl.absence.StudentAbsenceBakaRequest;
import xyz.slosa.endpoints.http.impl.login.LoginWithCredentialsBakaRequest;
import xyz.slosa.objects.impl.absence.AbsenceData;
import xyz.slosa.objects.impl.absence.AbsenceDataObject;
import xyz.slosa.objects.impl.absence.SubjectAbsenceData;
import xyz.slosa.objects.impl.login.LoginDataObject;

/**
 * @author slosa
 * @created 03.11.24, Sunday
 * CC BakalariDesktop's contributors, use according to the license!
 **/
public class Main {
    public static void main(String[] args) {
        BakalariAPI api = new BakalariAPI("https://www.gymnaziumjihlava.cz:8081/", System.out::println);
        final LoginDataObject loginDataObject = api.requestPost(new LoginWithCredentialsBakaRequest("", "")).join().getData();
        final String token = loginDataObject.token();

        final AbsenceDataObject data = api.request(new StudentAbsenceBakaRequest(), token).join().getData();

        final SubjectAbsenceData[] subjectAbsenceData = data.subjectAbsences();

        for (SubjectAbsenceData subjectAbsenceData1 : subjectAbsenceData) {
            System.out.println("name: " + subjectAbsenceData1.name());
            System.out.println("lessons: " +subjectAbsenceData1.lessonCount() + " missed: " + subjectAbsenceData1.base());
            System.out.println("------------");
        }
    }
}
