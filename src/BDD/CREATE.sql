DROP TABLE IF EXISTS utilisateur;
DROP TABLE IF EXISTS canal;
DROP TABLE IF EXISTS utilisateurcanal;
CREATE TABLE utilisateur(
    id INT AUTO_INCREMENT PRIMARY KEY,
    mail VARCHAR(30) NOT NULL,
    mdp VARCHAR(40) NOT NULL,
    nom VARCHAR(20) NOT NULL,
    prenom VARCHAR(20) NOT NULL,
    -- {client, admin}
    role VARCHAR(10) NOT NULL DEFAULT 'client',
    CONSTRAINT uni_compte UNIQUE(mail,mdp)
);

CREATE TABLE canal(
    id INT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(20) NOT NULL,
    descript VARCHAR(100) NOT NULL,
    date_cree DATE NOT NULL,
    date_espire DATE NOT NULL,
    CHECK(date_cree<date_espire),
    CONSTRAINT uni_canal UNIQUE(titre,descript,date_cree,date_espire)
);

CREATE TABLE utilisateurcanal(
    id INT AUTO_INCREMENT PRIMARY KEY,
    canal INT REFERENCES canal(id),
    membre INT REFERENCES utilisateur(id),
    CONSTRAINT uni_membre UNIQUE(canal,membre)
);