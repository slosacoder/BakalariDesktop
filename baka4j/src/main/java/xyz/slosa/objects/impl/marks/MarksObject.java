package xyz.slosa.objects.impl.marks;

import xyz.slosa.objects.BakaObject;

public record MarksObject(
        SubjectMarks[] subjectMarks
) implements BakaObject {
}
