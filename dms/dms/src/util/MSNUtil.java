package util;

import java.util.List;
import java.util.ArrayList;
import net.sf.jml.MsnContact;
import net.sf.jml.impl.MsnContactImpl;
import net.sf.jml.MsnMessenger;
import net.sf.jml.event.MsnContactListAdapter;
import net.sf.jml.impl.MsnMessengerFactory;
import net.sf.jml.MsnUserStatus;
import util.mail.bean.Contact;


public class MSNUtil {
    public MSNUtil() {

    }

    public static List<String> getContacts(String username, String password) {
        final List<String> list = new ArrayList<String>();
        MsnMessenger msn = null;
        try {

            msn = MsnMessengerFactory.createMsnMessenger(username,
                    password);
            msn.getOwner().setInitStatus(MsnUserStatus.ONLINE);
            msn.setLogIncoming(true);
            msn.setLogOutgoing(true);
//                msn.addListener(new MsnListener());
            msn.login();

            msn.addContactListListener(new MsnContactListAdapter() {
                public void contactStatusChanged(MsnMessenger msn,
                                                 MsnContact con) {
                }

                public void contactListInitCompleted(MsnMessenger messenger) {
                    MsnContact[] cons = messenger.getContactList().getContacts();
                    if (cons != null) {
                        for (MsnContact con : cons) {
                            list.add(con.getEmail().toString());
                        }
                    }
                }
            });

            long s = System.currentTimeMillis();
            long timeout = 60000;

            while (((System.currentTimeMillis() - s) < timeout) &&
                   (list.size() < 1)) {
                try {
                    Thread.sleep(1000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } finally {
            if (msn != null) {
                msn.logout();
            }
        }
        return list;
    }


    public static List<Contact> getMSNContacts(String username, String password) {
          final List<Contact> list = new ArrayList<Contact>();
          MsnMessenger msn = null;
          try {

              msn = MsnMessengerFactory.createMsnMessenger(username,
                      password);
              msn.getOwner().setInitStatus(MsnUserStatus.ONLINE);
              msn.setLogIncoming(true);
              msn.setLogOutgoing(true);
//                msn.addListener(new MsnListener());
              msn.login();

              msn.addContactListListener(new MsnContactListAdapter() {
                  public void contactStatusChanged(MsnMessenger msn,
                                                   MsnContact con) {
                  }

                  public void contactListInitCompleted(MsnMessenger messenger) {
                      MsnContact[] cons = messenger.getContactList().getContacts();
                      if (cons != null) {
                          for (MsnContact con : cons) {
                              Contact c=new Contact();
                              c.setName(con.getDisplayName());
                              c.setEmail(con.getEmail().toString());
                              c.setRemark(con.getPersonalMessage());
                              list.add(c);
                          }
                      }
                  }
              });

              long s = System.currentTimeMillis();
              long timeout = 60000;

              while (((System.currentTimeMillis() - s) < timeout) &&
                     (list.size() < 1)) {
                  try {
                      Thread.sleep(1000);
                  } catch (Exception ex) {
                      ex.printStackTrace();
                  }
              }

              try {
                  Thread.sleep(1000);
              } catch (Exception ex) {
                  ex.printStackTrace();
              }

          } finally {
              if (msn != null) {
                  msn.logout();
              }
          }
          return list;
      }



    public static void main(String args[]) {

        System.out.println("running...");
        long s=System.currentTimeMillis();
        List<Contact> list = getMSNContacts("name@gmail.com", "pwd");
        for ( Contact c : list) {
            System.out.println("name : "+c.getName());
            System.out.println("mail : " + c.getEmail());
        }
        System.out.println((System.currentTimeMillis()-s));
        System.out.println("running complete.");

    }


}
