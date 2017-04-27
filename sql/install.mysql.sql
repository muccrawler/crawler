/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  cmt
 * Created: 25.04.2017
 */
# TODO LISTE -> nicht verknüpft mit sites und host
create table sites_to_crawl (
    id int (10) AUTO_INCREMENT,
    url varchar(500) NOT NULL,
    priority double NOT NULL DEFAULT 0,   
    UNIQUE (url),
    PRIMARY KEY (id)
);

create table sites (
    id int (10) AUTO_INCREMENT,
    host_id int(10) NOT NULL,
    title varchar(500) NOT NULL DEFAULT '',
    metaDescription varchar(500) NOT NULL DEFAULT '',
    metaKeywords varchar(500) NOT NULL DEfAULT '',
    path varchar(500),
    rating double DEFAULT 0,
    PRIMARY KEY (id)
);

                                
# http://
# SELECT s.*, h.host as host, CONCAT (h.protocol + "://" + h.host + "/" s.path) 
#        AS url FROM sites AS s LEFT JOIN host as h ON h.id = s.host_id

create table host (
    id int (10) AUTO_INCREMENT,
    host varchar(500) NOT NULL,
    port int(5) NOT NULL DEFAULT '-1',
    protocol varchar(50) NOT NULL DEFAULT 'http',
    PRIMARY KEY(id)
);

create table images_to_crawl (
    id int(10) NOT NULL AUTO_INCREMENT,
    url varchar(500) NOT NULL,
    priority double DEFAULT 0.0,
    PRIMARY KEY(id)
);    

