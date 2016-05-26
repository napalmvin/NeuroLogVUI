# -*- coding: utf-8 -*-
# Generated by Django 1.9.4 on 2016-04-14 09:26
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('NeuroLogApp', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='ClinicalExams',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('format', models.CharField(max_length=8)),
                ('data', models.BinaryField()),
                ('taken', models.DateField(auto_now_add=True)),
                ('description', models.CharField(max_length=128, null=True)),
            ],
        ),
        migrations.CreateModel(
            name='GeneralPhysicalExams',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('taken', models.DateField(auto_now_add=True)),
                ('comments', models.CharField(max_length=128, null=True)),
                ('doctor', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='NeuroLogApp.Doctors')),
                ('patient', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='NeuroLogApp.Patients')),
            ],
        ),
        migrations.CreateModel(
            name='GenExamsAerialRecords',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('comments', models.CharField(max_length=128, null=True)),
                ('generalPhysicalExams', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='NeuroLogApp.GeneralPhysicalExams')),
            ],
        ),
        migrations.CreateModel(
            name='HeadacheRecords',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('onset', models.CharField(max_length=32)),
                ('onset_while', models.CharField(max_length=32)),
                ('location', models.CharField(max_length=64)),
                ('radiation', models.CharField(max_length=64)),
                ('severity_now', models.PositiveSmallIntegerField(verbose_name={'max_value': 10})),
                ('severity_at_worst', models.PositiveSmallIntegerField(verbose_name={'max_value': 10})),
                ('duration_and_timing', models.CharField(max_length=64)),
                ('characterized', models.CharField(max_length=64)),
                ('provocative_factors', models.CharField(max_length=64)),
                ('palliating_factors', models.CharField(max_length=64)),
                ('boolfield_data', models.PositiveIntegerField()),
                ('generalPhysicalExams', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='NeuroLogApp.GeneralPhysicalExams')),
                ('patient', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='NeuroLogApp.Patients')),
            ],
        ),
        migrations.CreateModel(
            name='NeurologicalExams',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('gait', models.PositiveSmallIntegerField(choices=[(0, 'normal:steady coordinated gait'), (1, 'abnormal:an unsteady uncoordinated gait'), (2, 'abnormal:a slow unsteady gait'), (3, 'abnormal:walks on heels and toes with out problems'), (4, 'abnormal:has difficulty with walking')], default=0)),
                ('gait_descr', models.CharField(max_length=64)),
                ('rhomberg', models.BooleanField(default=False)),
                ('rapid_alternating_movements', models.PositiveSmallIntegerField(choices=[(0, 'normal'), (1, 'abnormal')], default=0)),
                ('rapid_alternating_movements_desc', models.CharField(max_length=64)),
                ('cranial_nerves', models.PositiveSmallIntegerField(choices=[(0, 'intact and symmetric at upper and lower extremities bilaterally'), (1, 'abnormal')], default=0)),
                ('cranial_nerves_description', models.CharField(max_length=64)),
                ('sensation', models.PositiveSmallIntegerField(choices=[(0, 'intact and symmetric at upper and lower extremities bilaterally'), (1, 'abnormal')], default=0)),
                ('sensation_descr', models.CharField(blank=True, max_length=64, null=True)),
                ('strength', models.PositiveSmallIntegerField(choices=[(0, 'intact and symmetric at upper and lower extremities bilaterally'), (1, 'abnormal')], default=0)),
                ('strength_descr', models.CharField(blank=True, max_length=64, null=True)),
                ('right_biceps', models.PositiveSmallIntegerField(choices=[(-1, '-1 (Not examined)'), (0, '0 (absent)'), (1, '1+ (hypoactive'), (2, '2+ (normal)'), (3, '3+ (hyperactive without clonus)'), (4, '4+ (hyperactive with clonus)')], default=0)),
                ('left_biceps', models.PositiveSmallIntegerField(choices=[(-1, '-1 (Not examined)'), (0, '0 (absent)'), (1, '1+ (hypoactive'), (2, '2+ (normal)'), (3, '3+ (hyperactive without clonus)'), (4, '4+ (hyperactive with clonus)')], default=0)),
                ('right_triceps', models.PositiveSmallIntegerField(choices=[(-1, '-1 (Not examined)'), (0, '0 (absent)'), (1, '1+ (hypoactive'), (2, '2+ (normal)'), (3, '3+ (hyperactive without clonus)'), (4, '4+ (hyperactive with clonus)')], default=0)),
                ('left_triceps', models.PositiveSmallIntegerField(choices=[(-1, '-1 (Not examined)'), (0, '0 (absent)'), (1, '1+ (hypoactive'), (2, '2+ (normal)'), (3, '3+ (hyperactive without clonus)'), (4, '4+ (hyperactive with clonus)')], default=0)),
                ('right_forearm', models.PositiveSmallIntegerField(choices=[(-1, '-1 (Not examined)'), (0, '0 (absent)'), (1, '1+ (hypoactive'), (2, '2+ (normal)'), (3, '3+ (hyperactive without clonus)'), (4, '4+ (hyperactive with clonus)')], default=0)),
                ('left_forearm', models.PositiveSmallIntegerField(choices=[(-1, '-1 (Not examined)'), (0, '0 (absent)'), (1, '1+ (hypoactive'), (2, '2+ (normal)'), (3, '3+ (hyperactive without clonus)'), (4, '4+ (hyperactive with clonus)')], default=0)),
                ('right_patella', models.PositiveSmallIntegerField(choices=[(-1, '-1 (Not examined)'), (0, '0 (absent)'), (1, '1+ (hypoactive'), (2, '2+ (normal)'), (3, '3+ (hyperactive without clonus)'), (4, '4+ (hyperactive with clonus)')], default=0)),
                ('left_patella', models.PositiveSmallIntegerField(choices=[(-1, '-1 (Not examined)'), (0, '0 (absent)'), (1, '1+ (hypoactive'), (2, '2+ (normal)'), (3, '3+ (hyperactive without clonus)'), (4, '4+ (hyperactive with clonus)')], default=0)),
                ('right_ankle', models.PositiveSmallIntegerField(choices=[(-1, '-1 (Not examined)'), (0, '0 (absent)'), (1, '1+ (hypoactive'), (2, '2+ (normal)'), (3, '3+ (hyperactive without clonus)'), (4, '4+ (hyperactive with clonus)')], default=0)),
                ('left_ankle', models.PositiveSmallIntegerField(choices=[(-1, '-1 (Not examined)'), (0, '0 (absent)'), (1, '1+ (hypoactive'), (2, '2+ (normal)'), (3, '3+ (hyperactive without clonus)'), (4, '4+ (hyperactive with clonus)')], default=0)),
                ('babinski', models.BooleanField(default=False)),
                ('other', models.CharField(blank=True, max_length=64, null=True)),
                ('generalPhysicalExams', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='NeuroLogApp.GeneralPhysicalExams')),
                ('patient', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='NeuroLogApp.Patients')),
            ],
        ),
        migrations.CreateModel(
            name='PainRecords',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('location', models.CharField(max_length=32)),
                ('characterized', models.CharField(max_length=64)),
                ('radiation', models.CharField(max_length=32)),
                ('severity_now', models.PositiveSmallIntegerField(verbose_name={'max_value': 10})),
                ('severity_at_worst', models.PositiveSmallIntegerField(verbose_name={'max_value': 10})),
                ('duration_and_timing', models.CharField(max_length=64)),
                ('provocative_factors', models.CharField(max_length=64)),
                ('palliating_factors', models.CharField(max_length=64)),
                ('pain_beliefs_or_expectations', models.CharField(max_length=64)),
                ('cause', models.CharField(max_length=64)),
                ('meaning', models.CharField(max_length=64)),
                ('impact', models.CharField(max_length=64)),
                ('treatment', models.CharField(max_length=64)),
                ('goals', models.CharField(max_length=64)),
                ('involvement', models.CharField(max_length=64)),
                ('generalPhysicalExams', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='NeuroLogApp.GeneralPhysicalExams')),
                ('patient', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='NeuroLogApp.Patients')),
            ],
        ),
        migrations.AddField(
            model_name='clinicalexams',
            name='generalPhysicalExams',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='NeuroLogApp.GeneralPhysicalExams'),
        ),
        migrations.AddField(
            model_name='clinicalexams',
            name='patient',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='NeuroLogApp.Patients'),
        ),
    ]
