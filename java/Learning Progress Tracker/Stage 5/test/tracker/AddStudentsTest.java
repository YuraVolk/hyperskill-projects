package tracker;

import org.junit.Test;
import tracker.commands.AddStudentsCommand;
import static org.junit.jupiter.api.Assertions.*;

public class AddStudentsTest {
    @Test
    public void invalidNames() {
        AddStudentsCommand command = new AddStudentsCommand();
        String[] invalidNames = new String[] {
            "name surname",
            "n surname email@email.xyz",
            "'name surname email@email.xyz",
            "-name surname email@email.xyz",
            "name- surname email@email.xyz",
            "name' surname email@email.xyz",
            "nam-'e surname email@email.xyz",
            "na'-me surname email@email.xyz",
            "na--me surname email@email.xyz",
            "na''me surname email@email.xyz",
            "námé surname email@email.xyz",
            "name s email@email.xyz",
            "name -surname email@email.xyz",
            "name 'surname email@email.xyz",
            "name surnam''e email@email.xyz",
            "name surn--ame email@email.xyz",
            "name s'-urname email@email.xyz",
            "name su-'rname email@email.xyz",
            "name surname- email@email.xyz",
            "name surname' email@email.xyz",
            "name surnámé email@email.xyz",
            "name surname emailemail.xyz",
            "name surname email@emailxyz",
            "name surname email@e@mail.xyz"
        };

        for (var name : invalidNames) {
            assertNull(command.validateStudent(name));
        }
    }

    @Test
    public void correctNames() {
        AddStudentsCommand command = new AddStudentsCommand();
        String[][] correctNames = new String[][]{ {"John Smith jsmith@hotmail.com", "John"}, {"Anny Doolittle anny.md@mail.edu", "Anny"},
                {"Jean-Claude O'Connor jcda123@google.net", "Jean-Claude"}, {"Mary Emelianenko 125367at@zzz90.z9", "Mary"},
                {"Al Owen u15da125@a1s2f4f7.a1c2c5s4", "Al"}, {"Robert Jemison Van de Graaff robertvdgraaff@mit.edu", "Robert"},
                {"Ed Eden a1@a1.a1", "Ed"}, {"na'me s-u ii@ii.ii", "na'me"}, {"n'a me su aa-b'b ab@ab.ab", "n'a"}, {"nA me 1@1.1", "nA"}};
        for (var nameInfo : correctNames) {
            assertEquals(command.validateStudent(nameInfo[0]).firstName(), nameInfo[1]);
        }
    }

    @Test
    public void correctLastNames() {
        AddStudentsCommand command = new AddStudentsCommand();
        String[][] correctNames = new String[][]{ {"John Smith jsmith@hotmail.com", "Smith"}, {"Anny Doolittle anny.md@mail.edu", "Doolittle"},
                {"Jean-Claude O'Connor jcda123@google.net", "O'Connor"}, {"Mary Emelianenko 125367at@zzz90.z9", "Emelianenko"},
                {"Al Owen u15da125@a1s2f4f7.a1c2c5s4", "Owen"}, {"Robert Jemison Van de Graaff robertvdgraaff@mit.edu", "Jemison Van de Graaff"},
                {"Ed Eden a1@a1.a1", "Eden"}, {"na'me s-u ii@ii.ii", "s-u"}, {"n'a me su aa-b'b ab@ab.ab", "me su aa-b'b"}, {"nA me 1@1.1", "me"}};
        for (var nameInfo : correctNames) {
            assertEquals(command.validateStudent(nameInfo[0]).lastName(), nameInfo[1]);
        }
    }

    @Test
    public void correctEmails() {
        AddStudentsCommand command = new AddStudentsCommand();
        String[][] correctNames = new String[][]{
                {"John Smith jsmith@hotmail.com", "jsmith@hotmail.com"},
                {"Anny Doolittle anny.md@mail.edu", "anny.md@mail.edu"},
                {"Jean-Claude O'Connor jcda123@google.net", "jcda123@google.net"},
                {"Mary Emelianenko 125367at@zzz90.z9", "125367at@zzz90.z9"},
                {"Al Owen u15da125@a1s2f4f7.a1c2c5s4", "u15da125@a1s2f4f7.a1c2c5s4"},
                {"Robert Jemison Van de Graaff robertvdgraaff@mit.edu", "robertvdgraaff@mit.edu"},
                {"Ed Eden a1@a1.a1", "a1@a1.a1"},
                {"na'me s-u ii@ii.ii", "ii@ii.ii"},
                {"n'a me su aa-b'b ab@ab.ab", "ab@ab.ab"},
                {"nA me 1@1.1", "1@1.1"}
        };
        for (var nameInfo : correctNames) {
            assertEquals(command.validateStudent(nameInfo[0]).email(), nameInfo[1]);
        }
    }
}
