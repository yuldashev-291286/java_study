package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Set;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName("test1"));
      app.goTo().home();
    } else {
      app.goTo().home();
    }
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstname("Ruslan").withLastname("Yuldashev").withAddress("Moscow")
              .withTelephone("8-495-716-45-78").withEmail("uldashev@inbox.ru").withGroup("test1"), true);
    }
  }

  @Test
  public void testContactModification() throws Exception {
    Set<ContactData> before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstname("Ruslan").withLastname("Yuldashev")
            .withAddress("Moscow").withTelephone("8-495-716-45-78").withEmail("uldashev@inbox.ru").withGroup("test1");
    int lastElementOfContacts = before.size();
    app.contact().modify(lastElementOfContacts, contact);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size());

    before.remove(modifiedContact);
    before.add(contact);
    Assert.assertEquals(before, after);
  }

}
