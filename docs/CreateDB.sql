-- Add cascades
-- Add indexes

CREATE TYPE SEX_T AS ENUM ('Male', 'Female');

CREATE TYPE RACE_T AS ENUM ('Caucasian', 'African','Mongoloid');

CREATE TYPE GEN_PHYSICAL_EXAMS_AREA AS ENUM ('GENERAL_APPEARANCE','HEAD', 'EYES','EARS','NOSE','THROAT','NECK','CARDIAC','LUNGS','ABDOMEN','MUSKOSKELETAL','BACK','EXTREMITIES','NEUROLOGICAL','SKIN','PSYCHIATRIC',
-- MALE
'RECTAL','GENITALIA',
-- FEMALE
'BREASTS','PELVIC');


-- present in django moels
CREATE TABLE DOCTORS (
  ID INTEGER   NOT NULL ,
  NAME VARCHAR(32)   NOT NULL ,
  FAMILY_NAME VARCHAR(64)   NOT NULL ,
  BIRTHDAY DATE   NOT NULL   ,
  PHOTO BLOB,
  QUALLIFICATION VARCHAR(64)   NOT NULL ,
  SEX SEX_T  NOT NULL ,  
PRIMARY KEY(ID));


--+ present in django moels
CREATE TABLE PATIENTS (
  ID INTEGER   NOT NULL ,
  NAME VARCHAR(20)   NOT NULL ,
  FAMILY_NAME VARCHAR(45)   NOT NULL ,
  BIRTHDAY DATE   NOT NULL ,
  SEX SEX_T  NOT NULL , 
  RACE RACE_T  NOT NULL ,
  PHOTO BLOB      ,
  PREREQUISITE VARCHAR(128)  NOT NULL , --Religion or else
PRIMARY KEY(ID));



-- Avoid general exams without aerial exam.
-- Add logical constraints for minimum number of aerial exams
-- Add male female check(in llogic)
--+ present in django models
CREATE TABLE GENERAL_PHYSICAL_EXAMS (
  ID INTEGER   NOT NULL ,
  DOCTORS_ID INTEGER   NOT NULL ,
  PATIENTS_ID INTEGER   NOT NULL ,
  TAKEN DATETIME NOT NULL,
  COMMENTS VARCHAR(128),
PRIMARY KEY(ID)  ,
  FOREIGN KEY(DOCTORS_ID) REFERENCES DOCTORS(ID),
  FOREIGN KEY(PATIENTS_ID) REFERENCES PATIENTS(ID),
  );
	
	--+ present in django models
CREATE TABLE GENERAL_PHYSICAL_EXAMS_AEIRAL_RECORD (
  ID INTEGER   NOT NULL ,
  AREA GEN_PHYSICAL_EXAMS_AREA  NOT NULL ,
  GENERAL_PHYSICAL_EXAM_ID INTEGER   NOT NULL ,
  DESCRIPTION VARCHAR(128) NOT NULL ,
PRIMARY KEY(ID)  ,
  FOREIGN KEY(GENERAL_PHYSICAL_EXAMS_ID) REFERENCES GENERAL_PHYSICAL_EXAMS(ID));

-----------------------------------------------------------------------------------------

--+ present in django models
CREATE TABLE CLINICAL_EXAMS (
  ID INTEGER   NOT NULL ,
  PATIENTS_ID INTEGER   NOT NULL ,
  DESCRIPTION VARCHAR(256)   NOT NULL ,
  FORMAT_ VARCHAR(32)   NOT NULL ,
  DATA_ BLOB   NOT NULL ,
  TAKEN DATETIME   NOT NULL ,
  COMMENTS VARCHAR(128)      ,
PRIMARY KEY(ID)  ,
  FOREIGN KEY(PATIENTS_ID) REFERENCES PATIENTS(ID));
  

--+ present in django models  
create table  pain_records(
	id integer   not null ,
	patients_id integer   not null ,
	location varchar(64)   not null ,
	characterized  varchar(64)   not null ,
	radiation varchar(64)   not null ,
	severity_now smallint  not null ,--0-10
	severity_at_worst  smallint  not null ,--0-10
	duration_and_timing varchar(64)   not null ,
	provocative_factors varchar(64)   not null ,
	palliating_factors varchar(64)   not null ,

	pain_beliefs_or_expectations  varchar(64)   not null ,
	cause  varchar(64)   not null ,-- what is causing my pain?
	meaning varchar(64)   not null ,  -- work will worsen my pain because...
	impact varchar(64)   not null ,  -- on my work/social/recreational/quality of life in general.
	treatment varchar(64)   not null ,  -- what needs to be done now and in the future for my pain.
	goals varchar(64)   not null ,  -- what i expect to achieve with treatment.
	involvement varchar(64)   not null ,  -- how involved will i be in my treatment?
primary key(id)  ,
  foreign key(patients_id) references patients(id));
);
----------------------------------------------------
CREATE TYPE GAIT_T AS ENUM ('normal:steady coordinated gait',
 'abnormal:an unsteady uncoordinated gait',
 'abnormal:a slow unsteady gait',
 'abnormal:walks on heels and toes with out problems',
 'abnormal:has difficulty with walking');

CREATE TYPE ABNORMALITY_T AS ENUM ('normal','abnormal');
CREATE TYPE REFLEXES_T  AS ENUM ('-1 (Not examined)','0 (absent)','1+ (hypoactive','2+ (normal)','3+ (hyperactive without clonus)','4+ (hyperactive with clonus)');
CREATE TYPE CN_II_XII AS ENUM('intact and symmetric at upper and lower extremities bilaterally','abnormal');

CREATE TABLE  NEUROLOGICAL_EXAMS(
	ID INTEGER   NOT NULL ,
	PATIENTS_ID INTEGER   NOT NULL ,
	GENERAL_PHYSICAL_EXAM_ID INTEGER   NOT NULL ,
	
	gait gait_t  not null ,
	-- check for mandatory record in abnormal case
	gait_description varchar(64) ,

	rhomberg boolean,
	rapid_alternating_movements abnormality_t not null ,
	-- check for mandatory record in abnormal case
	rapid_alternating_movements_description varchar(64) ,
	
	cranial_nerves enum ('ii-xii intact','abnormal')  default 'ii-xii intact',
	cranial_nerves_description varchar(64) ,
	-- cranial nerves ii-xii intact=true.
	sensation  cn_ii_xii default 'intact and symmetric at upper and lower extremities bilaterally',
	sensation_description varchar(64) ,
	strength   cn_ii_xii default 'intact and symmetric at upper and lower extremities bilaterally',
	strength_description varchar(64) ,
	
	-- reflexes:
	right_biceps  reflexes_t default   '-1 (not examined)',
	left_biceps   reflexes_t default   '-1 (not examined)',  
	right_triceps reflexes_t default   '-1 (not examined)',
	left_triceps  reflexes_t default   '-1 (not examined)', 
	right_forearm reflexes_t default   '-1 (not examined)', 
	left_forearm  reflexes_t default   '-1 (not examined)',
	right_patella reflexes_t default   '-1 (not examined)',
	left_patella  reflexes_t default   '-1 (not examined)',
	right_ankle   reflexes_t default   '-1 (not examined)',
	left_ankle    reflexes_t default   '-1 (not examined)',  
	
	babinski boolean, --[select value="negative|postive"]  --
	other varchar(64)   not null , --the following other neurologic findings were found: [textarea default="none"]
primary key(id)  ,
  foreign key(patients_id) references patients(id),
  foreign key(general_physical_exams_id) references general_physical_exams(id)
);


create table headache_records(
	id integer  not null ,
	patients_id integer   not null ,	
	onset  varchar(32) not null ,-- prior to evaluation
	onset_while   varchar(32)  not null ,
	-- -associated_pain  (0=none, 10=severe)
	location varchar(64)   not null ,
	radiation varchar(64)   not null ,
	severity_now smallint  not null ,--0-10
	severity_at_worst  smallint  not null ,--0-10
	duration_and_timing varchar(64)   not null ,
	characterized  varchar(64)   not null ,
	-- pain modifiers
	provocative_factors varchar(64)   not null ,--worse_with --
	palliating_factors varchar(64)   not null ,--relieved_with --
	
	
	
	-- worth_with_brght_light boolean, <-- worse with bright light exposure.
	-- associated symptoms://lets use smallint or int or long field
	/* nausea_vomiting boolean, -- nausea/vomiting
	aura boolean,-- preceding aura before the headache (e.g. vision change, scotomata)
	blurred_vision boolean,-- blurred vision
	fewer boolean,-- fever
	sinus_press_or_nasal_drain boolean,-- sinus pressure or nasal drainage
	extremity_weakness boolean,-- extremity weakness

	-- pertinent pmh
	migraine_headache boolean,-- migraine headache
	frequent_sinusitis boolean,-- frequent sinusitis
	glaucoma boolean,-- glaucoma
	head_trauma boolean,-- head trauma
	cns_riscs boolean,-- serious cns risks (e.g. active cancer, immunosuppression, hiv)
	exposures boolean,-- exposures (e.g. tick bites, carbon monoxide)
	family_history boolean, */-- family history of cerebral aneurysm or stroke
	
	boolfield_data integer  not null ,
);


create table nih_stroke_scale (
-- 1a. assess level of consciousness (alert=0, coma=3)
    conscious  enum ('alert (0 points)','not alert; arousable by minor stimulation (1 point)','not alert; requires repeated stimulation to attend (2 points)','not responsive or reflexive posturing only (3 points)'),

-- 1b. assess orientation: month, age (1 point per bad answer)
    orientation  enum ('answers both questions correctly (0 points)','answers one question correctly (1 point)','answers neither question correctly (2 points)'),

-- 1c. follow commands: open and close eyes, make fist and release (1 point per command not obeyed)
    commands  enum ('performs both tasks correctly (0 points)','performs one task correctly (1 point)','performs neither task correctly (2 points)'),

-- 2. follow my finger (normal=0, forced deviation=2)
    finger  enum ('normal (0 points)','partial gaze palsy (1 point)','forced deviation (2 points)'),

-- 3. visual field (normal=0, hemianopia=2, bilateral loss=3)
    visual  enum ('no visual loss (0 points)','partial hemianopia (1 point)','complete hemianopia (2 points)','bilateral loss (3 points)'),

-- 4. facial palsy:  show teeth, raise eyebrows, squeeze eyes shut (normal=0, complete=3) 
    face  enum ('normal (0 points)','minor paralysis (1 point)','partial paralysis (2 points)','complete paralysis (3 points)'),

-- 5a. motor strength left arm: elevate to 90 degrees
    left_arm  enum ('no drift (0 points)','drift (1 point)','some effort against gravity (2 points)','no effort against gravity (3 points)','no movement (4 points)','amputation or joint fusion (0 points)'),

-- 5b. motor strength right arm: elevate to 90 degrees
    right_arm  enum ('no drift (0 points)','drift (1 point)','some effort against gravity (2 points)','no effort against gravity (3 points)','no movement (4 points)','amputation or joint fusion (0 points)'),

-- 6a. motor strength left leg: elevate to 30 degrees
    left_leg  enum ('no drift (0 points)','drift (1 point)','some effort against gravity (2 points)','no effort against gravity (3 points)','no movement (4 points)','amputation or joint fusion (0 points)'),

-- 6b. motor strength right leg: elevate to 30 degrees
    right_leg  enum ('no drift (0 points)','drift (1 point)','some effort against gravity (2 points)','no effort against gravity (3 points)','no movement (4 points)','amputation or joint fusion (0 points)'),

-- 7. coordination or limb ataxia: finger-nose-finger, heel-knee-shin (absent=0, both limbs=2)
    ataxia  enum ('absent (0 points)','ataxia in one limb (1 point)','ataxia in two limbs (2 points)'),

-- 8. sensory: pin prick to face, arm, trunk, and legs, compare sides (normal=0, severe loss=2)
    sensory  enum ('normal (0 points)','mild to moderate loss (1 point)','severe to total loss (2 points)'),

-- 9. language: name items, describe picture, read sentences (no aphasia=0, mute=3)
    language  enum ('no aphasia (0 points)','mild to moderate aphasia (1 point)','severe aphasia (2 points)','mute, global aphasia (3 points)'),

-- 10. dysarthria: speech clarity while reading word list (normal=0, nearly unintelligible=2)
    dysarthria  enum ('normal (0 points)','mild to moderate dysarthria (1 point)','severe dysarthria (2 points)','intubated or physical barrier (0 points)'),

-- 11. extinction and inattention: formerly called 'neglect' (none=0, complete=2)
    extinction  enum ('no abnormality (0 points)','visual, tactile, auditory, spatial, or personal inattention in one sensory modality (1 point)','profound hemi-inattention or extinction to more than one modality (2 points)'),


/*total score:  [calc value="(conscious)+(orientation)+(commands)+(finger)+(visual)+(face)+(left_arm)+(right_arm)+(left_leg)+(right_leg)+(ataxia)+(sensory)+(language)+(dysarthria)+(extinction)" memo="score"] (maximum=42)*/
);


-- cha(2)ds(2)-vasc score for nonvalvular atrial fibrillation[/html]
create table chads2_vasc(
congestive heart failure 'no (0 points)=0|yes (1 point)=1'),-- congestive heart failure
hypertension 'no (0 points)=0|yes (1 point)=1'),-- hypertension
age 'less than 65 years (0 points)=0|65-74 years (1 point)=1|75 years or over (2 points)=2'),-- age
diabetes_mellitus'no (0 points)=0|yes (1 point)=1'),-- diabetes mellitus
stroke_tia_thromboembolic'no (0 points)=0|yes (2 points)=2'),-- stroke/tia/thromboembolic
vascular_disease 'no (0 points)=0|yes (1 point)=1'),-- vascular disease  (prior mi, pad, or aortic plaque)
sex'no (0 points)=0|yes (1 point)=1'),-- sex category - female
/*score --> [calc memo="number" value="score1=(q1)+(q2)+(q3)+(q4)+(q5)+(q6)+(q7)"] / 9 points
result --> [calc memo="interpretation" value="score2=(q1)+(q2)+(q3)+(q4)+(q5)+(q6)+(q7);score2>1?'recommend oral anticoagulation':score2>0?'recommend antithrombotic therapy with oral anticoagulation or antiplatelet therapy but preferably oral anticoagulation':'recommend no antithrombotic therapy'"]*/
)
