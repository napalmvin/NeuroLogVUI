from django.db import models


SEX_CHOICES=((0,'Male'),
             (1,'Female'))

RACE_CHOICES=((0,'Caucasian'),
             (1,'African'),
             (2,'Mongoloid'))

PHYSICAL_EXAM_AREA= ((0,'GENERAL_APPEARANCE'),
                    (1,'HEAD'),
                    (2,'EYES'),
                    (3,'EARS'),
                    (4,'NOSE'),
                    (5,'THROAT'),
                    (6,'NECK'),
                    (7,'CARDIAC'),
                    (8,'LUNGS'),
                    (9,'ABDOMEN'),
                    (10,'MUSKOSKELETAL'),
                    (11,'BACK'),
                    (12,'EXTREMITIES'),
                    (13,'NEUROLOGICAL'),
                    (14,'SKIN'),
                    (15,'PSYCHIATRIC'),
                    # MALE
                    (16,'RECTAL'),
                    (17,'GENITALIA'),
                    # FEMALE
                    (18,'BREASTS'),
                    (19,'PELVIC'))


class Patients(models.Model): 
    first_name = models.CharField(max_length=30)
    last_name = models.CharField(max_length=45)
    birth_date = models.DateField(auto_now_add=True)
    sex = models.SmallIntegerField(choices=SEX_CHOICES)
    race = models.SmallIntegerField(choices=RACE_CHOICES)
    photo = models.FileField(null=True)
    prerequisite= models.CharField(max_length=125)

class Doctors(models.Model):
    first_name = models.CharField(max_length=30)
    last_name = models.CharField(max_length=45,db_index=True)
    birth_date = models.DateField(auto_now_add=False,db_index=True)
    sex = models.SmallIntegerField(choices=SEX_CHOICES)
    race = models.SmallIntegerField(choices=RACE_CHOICES)
    photo = models.FileField(null=True)
    qualification = models.CharField(max_length=30)




    
class GeneralPhysicalExams(models.Model):
    doctor = models.ForeignKey(Doctors,on_delete=models.CASCADE)
    patient = models.ForeignKey(Patients,on_delete=models.CASCADE)
    taken = models.DateField(auto_now_add=True)
    comments = models.CharField(max_length=128,null=True)
    
    
class GenExamsAerialRecords(models.Model):
    generalPhysicalExams = models.ForeignKey(GeneralPhysicalExams,on_delete=models.CASCADE)
    comments = models.CharField(max_length=128,null=True)

class ClinicalExams(models.Model):    
    patient = models.ForeignKey(Patients, on_delete=models.CASCADE)
    generalPhysicalExams = models.ForeignKey(GeneralPhysicalExams,on_delete=models.CASCADE)
    description = models.CharField(max_length=128, null=True, blank=True)
    format = models.CharField(max_length=8)  # file extension
    data = models.FileField()
    taken = models.DateField(auto_now_add=True) 
    description = models.CharField(max_length=128, null=True)
        
class PainRecords(models.Model):
    patient = models.ForeignKey(Patients, on_delete=models.CASCADE)
    generalPhysicalExams = models.ForeignKey(GeneralPhysicalExams,on_delete=models.CASCADE)
    
    location = models.CharField(max_length=32)
    characterized = models.CharField(max_length=64)
    radiation = models.CharField(max_length=32)
    severity_now = models.PositiveSmallIntegerField({'max_value':10})  # 0-10
    severity_at_worst = models.PositiveSmallIntegerField({'max_value':10}) # 0-10
    duration_and_timing = models.CharField(max_length=64)
    provocative_factors = models.CharField(max_length=64)
    palliating_factors = models.CharField(max_length=64)
    pain_beliefs_or_expectations = models.CharField(max_length=64)
    
    cause = models.CharField(max_length=64)  # what is causing my pain?
    meaning = models.CharField(max_length=64)  # work will worsen my pain because...
    impact = models.CharField(max_length=64)  # -- on my work/social/recreational/quality of life in general.
    treatment = models.CharField(max_length=64)  # -- what needs to be done now and in the future for my pain.
    goals = models.CharField(max_length=64)  # -- what i expect to achieve with treatment.
    involvement = models.CharField(max_length=64)  # -- how involved will i be in my treatment?

# 
class HeadacheRecords(models.Model):
    patient = models.ForeignKey(Patients, on_delete=models.CASCADE)
    generalPhysicalExams = models.ForeignKey(GeneralPhysicalExams,on_delete=models.CASCADE)

    onset  = models.CharField(max_length=32)#-- prior to evaluation
    onset_while = models.CharField(max_length=32)
#     -- -associated_pain  (0=none, 10=severe)
    location = models.CharField(max_length=64)
    radiation = models.CharField(max_length=64)
    severity_now= models.PositiveSmallIntegerField({'max_value':10}) # 0-10
    severity_at_worst = models.PositiveSmallIntegerField({'max_value':10}) # 0-10
    duration_and_timing= models.CharField(max_length=64)
    characterized= models.CharField(max_length=64)
#     -- pain modifiers
    provocative_factors= models.CharField(max_length=64)#--worse_with --
    palliating_factors= models.CharField(max_length=64)#--relieved_with -- 
#     -- worth_with_brght_light boolean, <-- worse with bright light exposure.
#     -- associated symptoms://lets use smallint or int or long field
#     ''' nausea_vomiting boolean, -- nausea/vomiting
#     aura boolean,-- preceding aura before the headache (e.g. vision change, scotomata)
#     blurred_vision boolean,-- blurred vision
#     fewer boolean,-- fever
#     sinus_press_or_nasal_drain boolean,-- sinus pressure or nasal drainage
#     extremity_weakness boolean,-- extremity weakness
#  
#     -- pertinent pmh
#     migraine_headache boolean,-- migraine headache
#     frequent_sinusitis boolean,-- frequent sinusitis
#     glaucoma boolean,-- glaucoma
#     head_trauma boolean,-- head trauma
#     cns_riscs boolean,-- serious cns risks (e.g. active cancer, immunosuppression, hiv)
#     exposures boolean,-- exposures (e.g. tick bites, carbon monoxide)
#     family_history boolean, */-- family history of cerebral aneurysm or stroke
#     '''
    boolfield_data =models.PositiveIntegerField()

 

GAIT_T = ((0, 'normal:steady coordinated gait'),
         (1, 'abnormal:an unsteady uncoordinated gait'),
         (2, 'abnormal:a slow unsteady gait'),
         (3, 'abnormal:walks on heels and toes with out problems'),
         (4, 'abnormal:has difficulty with walking'))

ABNORMALITY_T=((0,'normal'),(1,'abnormal'))

REFLEXES_T = ((-1, '-1 (Not examined)'),
             (0, '0 (absent)'),
             (1, '1+ (hypoactive'),
             (2, '2+ (normal)'),
             (3, '3+ (hyperactive without clonus)'), 
             (4, '4+ (hyperactive with clonus)'))
             
CN_II_XII =((0,'intact and symmetric at upper and lower extremities bilaterally'),
            (1,'abnormal'))

class NeurologicalExams(models.Model):
    patient = models.ForeignKey(Patients, on_delete=models.CASCADE)
    generalPhysicalExams = models.ForeignKey(GeneralPhysicalExams, on_delete=models.CASCADE)
    gait = models.PositiveSmallIntegerField(choices=GAIT_T, default=0)
    # check for mandatory record in abnormal case
    gait_descr = models.CharField(max_length=64) 

    rhomberg = models.BooleanField(default=False)
    
    rapid_alternating_movements = models.PositiveSmallIntegerField(choices=ABNORMALITY_T, default=0)
#     -- check for mandatory record in abnormal case
    rapid_alternating_movements_desc = models.CharField(max_length=64)
    cranial_nerves = models.PositiveSmallIntegerField(choices=CN_II_XII,default=0)
    cranial_nerves_description = models.CharField(max_length=64)
#     -- cranial nerves ii-xii intact=true.
    sensation   = models.PositiveSmallIntegerField(choices=CN_II_XII,default=0)
    sensation_descr = models.CharField(max_length=64, null=True, blank=True) 
    strength    = models.PositiveSmallIntegerField(choices=CN_II_XII,default=0)
    strength_descr =models.CharField(max_length=64,null=True,blank=True) 
#     
#     -- reflexes:
    right_biceps = models.PositiveSmallIntegerField(choices=REFLEXES_T, default=0)
    left_biceps = models.PositiveSmallIntegerField(choices=REFLEXES_T, default=0)
    right_triceps = models.PositiveSmallIntegerField(choices=REFLEXES_T, default=0)
    left_triceps = models.PositiveSmallIntegerField(choices=REFLEXES_T, default=0)
    right_forearm = models.PositiveSmallIntegerField(choices=REFLEXES_T, default=0)
    left_forearm = models.PositiveSmallIntegerField(choices=REFLEXES_T, default=0)
    right_patella = models.PositiveSmallIntegerField(choices=REFLEXES_T, default=0)
    left_patella = models.PositiveSmallIntegerField(choices=REFLEXES_T, default=0)
    right_ankle = models.PositiveSmallIntegerField(choices=REFLEXES_T, default=0)
    left_ankle = models.PositiveSmallIntegerField(choices=REFLEXES_T, default=0)
     
    babinski = models.BooleanField(default=False)
    other = models.CharField(max_length=64, null=True, blank=True) 

# create table nih_stroke_scale (
# -- 1a. assess level of consciousness (alert=0, coma=3)
#     conscious  enum ('alert (0 points)','not alert; arousable by minor stimulation (1 point)','not alert; requires repeated stimulation to attend (2 points)','not responsive or reflexive posturing only (3 points)'),
# 
# -- 1b. assess orientation: month, age (1 point per bad answer)
#     orientation  enum ('answers both questions correctly (0 points)','answers one question correctly (1 point)','answers neither question correctly (2 points)'),
# 
# -- 1c. follow commands: open and close eyes, make fist and release (1 point per command not obeyed)
#     commands  enum ('performs both tasks correctly (0 points)','performs one task correctly (1 point)','performs neither task correctly (2 points)'),
# 
# -- 2. follow my finger (normal=0, forced deviation=2)
#     finger  enum ('normal (0 points)','partial gaze palsy (1 point)','forced deviation (2 points)'),
# 
# -- 3. visual field (normal=0, hemianopia=2, bilateral loss=3)
#     visual  enum ('no visual loss (0 points)','partial hemianopia (1 point)','complete hemianopia (2 points)','bilateral loss (3 points)'),
# 
# -- 4. facial palsy:  show teeth, raise eyebrows, squeeze eyes shut (normal=0, complete=3) 
#     face  enum ('normal (0 points)','minor paralysis (1 point)','partial paralysis (2 points)','complete paralysis (3 points)'),
# 
# -- 5a. motor strength left arm: elevate to 90 degrees
#     left_arm  enum ('no drift (0 points)','drift (1 point)','some effort against gravity (2 points)','no effort against gravity (3 points)','no movement (4 points)','amputation or joint fusion (0 points)'),
# 
# -- 5b. motor strength right arm: elevate to 90 degrees
#     right_arm  enum ('no drift (0 points)','drift (1 point)','some effort against gravity (2 points)','no effort against gravity (3 points)','no movement (4 points)','amputation or joint fusion (0 points)'),
# 
# -- 6a. motor strength left leg: elevate to 30 degrees
#     left_leg  enum ('no drift (0 points)','drift (1 point)','some effort against gravity (2 points)','no effort against gravity (3 points)','no movement (4 points)','amputation or joint fusion (0 points)'),
# 
# -- 6b. motor strength right leg: elevate to 30 degrees
#     right_leg  enum ('no drift (0 points)','drift (1 point)','some effort against gravity (2 points)','no effort against gravity (3 points)','no movement (4 points)','amputation or joint fusion (0 points)'),
# 
# -- 7. coordination or limb ataxia: finger-nose-finger, heel-knee-shin (absent=0, both limbs=2)
#     ataxia  enum ('absent (0 points)','ataxia in one limb (1 point)','ataxia in two limbs (2 points)'),
# 
# -- 8. sensory: pin prick to face, arm, trunk, and legs, compare sides (normal=0, severe loss=2)
#     sensory  enum ('normal (0 points)','mild to moderate loss (1 point)','severe to total loss (2 points)'),
# 
# -- 9. language: name items, describe picture, read sentences (no aphasia=0, mute=3)
#     language  enum ('no aphasia (0 points)','mild to moderate aphasia (1 point)','severe aphasia (2 points)','mute, global aphasia (3 points)'),
# 
# -- 10. dysarthria: speech clarity while reading word list (normal=0, nearly unintelligible=2)
#     dysarthria  enum ('normal (0 points)','mild to moderate dysarthria (1 point)','severe dysarthria (2 points)','intubated or physical barrier (0 points)'),
# 
# -- 11. extinction and inattention: formerly called 'neglect' (none=0, complete=2)
#     extinction  enum ('no abnormality (0 points)','visual, tactile, auditory, spatial, or personal inattention in one sensory modality (1 point)','profound hemi-inattention or extinction to more than one modality (2 points)'),
# 
# 
# /*total score:  [calc value="(conscious)+(orientation)+(commands)+(finger)+(visual)+(face)+(left_arm)+(right_arm)+(left_leg)+(right_leg)+(ataxia)+(sensory)+(language)+(dysarthria)+(extinction)" memo="score"] (maximum=42)*/
# );
# 
# 
# -- cha(2)ds(2)-vasc score for nonvalvular atrial fibrillation[/html]
# create table chads2_vasc(
# congestive heart failure 'no (0 points)=0|yes (1 point)=1'),-- congestive heart failure
# hypertension 'no (0 points)=0|yes (1 point)=1'),-- hypertension
# age 'less than 65 years (0 points)=0|65-74 years (1 point)=1|75 years or over (2 points)=2'),-- age
# diabetes_mellitus'no (0 points)=0|yes (1 point)=1'),-- diabetes mellitus
# stroke_tia_thromboembolic'no (0 points)=0|yes (2 points)=2'),-- stroke/tia/thromboembolic
# vascular_disease 'no (0 points)=0|yes (1 point)=1'),-- vascular disease  (prior mi, pad, or aortic plaque)
# sex'no (0 points)=0|yes (1 point)=1'),-- sex category - female
# /*score --> [calc memo="number" value="score1=(q1)+(q2)+(q3)+(q4)+(q5)+(q6)+(q7)"] / 9 points
# result --> [calc memo="interpretation" value="score2=(q1)+(q2)+(q3)+(q4)+(q5)+(q6)+(q7);score2>1?'recommend oral anticoagulation':score2>0?'recommend antithrombotic therapy with oral anticoagulation or antiplatelet therapy but preferably oral anticoagulation':'recommend no antithrombotic therapy'"]*/
# )
     
