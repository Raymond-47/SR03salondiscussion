INSERT INTO utilisateur(id,mail,mdp,nom,prenom,role) VALUES(0,"admin","g83ai719c5ha9ei1ec533jec3i6b7","admin","admin","admin");

INSERT INTO utilisateur(mail,mdp,nom,prenom) VALUES("u1@gmail.com","-dcdd6i3e28a78fh2ii2b1bj3cb1cc","u","1");

INSERT INTO utilisateur(mail,mdp,nom,prenom) VALUES("u2@gmail.com","j6e24gf75ic32264f652d6fe08ggj","u","2");

INSERT INTO utilisateur(mail,mdp,nom,prenom) VALUES("u3@gmail.com","213ce54b0h0jg934gh0gb0g1djf952","u","3");



INSERT INTO canal(titre,descript,date_cree,date_espire) VALUES("c1","canal 1","2022-05-07","9999-12-31");
INSERT INTO canal(titre,descript,date_cree,date_espire) VALUES("c2","canal 2","2022-06-01","2022-06-20");

INSERT INTO utilisateurcanal(canal,membre) VALUES(1,2);

INSERT INTO utilisateurcanal(canal,membre) VALUES(1,3);

INSERT INTO utilisateurcanal(canal,membre) VALUES(2,2);

INSERT INTO utilisateurcanal(canal,membre) VALUES(2,3);

INSERT INTO utilisateurcanal(canal,membre) VALUES(2,4);