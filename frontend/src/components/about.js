import React from 'react';


const About = () => (
    <div>
      <h2>Types of review processes</h2>
      <p>There are many variations of code review processes, some of which will be detailed below.</p>
   
      <h2>Formal inspection</h2>
      <p>The historically first code review process that was studied and described in detail was called "Inspection" by its inventor Michael Fagan.[6] This Fagan inspection is a formal process which involves a careful and detailed execution with multiple participants and multiple phases. Formal code reviews are the traditional method of review, in which software developers attend a series of meetings and review code line by line, usually using printed copies of the material. Formal inspections are extremely thorough and have been proven effective at finding defects in the code under review.</p>
      <p>An important byproduct of a properly conducted formal code review is a written record describing:</p>
      <ul>
        <li>Who: Names of those involved in the Review.</li>
        <li>When: Date and time the Review was conducted.</li>
        <li>Why: Best-Practice, Error Detection, Vulnerability Exposure, Malware Discovery or a combination.</li>
        <li>What: Name of the class, method, or program, plus line ranges and other particulars specific to the reviewed code.</li>
        <li>Result: What was disclosed during the course of the Review.</li>
      </ul>

      <h2>Regular change-based code review</h2>
      <p>In recent years,[when?] many teams in industry have introduced a more lightweight type of code review.[7][3] Its main characteristic is that the scope of each review is based on the changes to the codebase performed in a ticket, user story, commit, or some other unit of work. Furthermore, there are rules or conventions that embed the review task into the development process (e.g., "every ticket has to be reviewed"), instead of explicitly planning each review. Such a review process is called "regular, change-based code review". There are many variations of this basic process. A survey among 240 development teams from 2017 found that 90% of the teams use a review process that is based on changes (if they use reviews at all), and 60% use regular, change-based code review[3]. To perform change-based reviews smoothly, authors and reviewers use software tools: informal ones such as pastebins and IRC, or specialized tools designed for peer code review such as Gerrit and GitHub's pull requests.</p>
      <p>Change-based review is also referred to by other names, like "tool-assisted code review", "modern code review", "contemporary code review" or "differential code review". Many of these terms lack a definition that is precise enough to specify whether they are equal to or only similar to "regular, change-based code review".</p>

      <h2>Other types</h2>
      <p>In addition to the above-mentioned code review types, there are many more terms used to refer to certain variations of the process. Examples are:</p>
      <ul>
        <li>Over-the-shoulder – one developer looks over the author's shoulder as the latter walks through the code.</li>
        <li>Email pass-around – source code management system emails code to reviewers automatically after checkin is made.</li>
        <li>Walkthroughs, usually with an emphasis on the presentation and discussion of the code in a meeting.</li>
      </ul>
      <p>Pair programming, i.e., two authors that develop code together at the same workstation, is very similar to code review but not a code review in the narrow sense.</p>
      <p>The "IEEE Standard for Software Reviews and Audits" (IEEE 1028-2008) describes several variants of software reviews that can also be applied to code. </p>

      <h2>Efficiency and effectiveness of reviews</h2>
      <p>Code reviews require a considerable investment of effort from the software developers. Especially formal inspection has been criticized for this. The needed effort has to be outweighed by an adequate number of detected defects.</p>
      <p>Capers Jones' ongoing analysis of over 12,000 software development projects showed that the latent defect discovery rate of formal inspection is in the 60-65% range. For informal inspection, the figure is less than 50%. The latent defect discovery rate for most forms of testing is about 30%. A code review case study published in the book Best Kept Secrets of Peer Code Review found that lightweight reviews can uncover as many bugs as formal reviews, but were faster and more cost-effective[10] in contradiction to the study done by Capers Jones.</p>
      <p>The types of defects detected in code reviews have also been studied. Empirical studies provided evidence that up to 75% of code review defects affect software evolvability/maintainability rather than functionality, making code reviews an excellent tool for software companies with long product or system life cycles.</p>
      <p>Although most found issues are maintainability problems, high impact defects can be found, too. A special category of defects that often has a high impact are security vulnerabilities such as format string exploits, race conditions, memory leaks and buffer overflows, thereby improving software security. Guidelines</p>
      <p>The effectiveness of code review was found to depend on the speed of reviewing. Code review rates should be between 200 and 400 lines of code per hour. Inspecting and reviewing more than a few hundred lines of code per hour for critical software (such as safety critical embedded software) may be too fast to find errors.</p>
      <p>Some sources recommend to use a checklist of the most important problems for reviewing code. For example, a STIG [e.g., Application Security STIG 4.3] provides an excellent vulnerability checklist (although many controls are inapplicable and can be ignored). </p>

      <h2>Supporting tools</h2>
      <p>Many teams perform change-based code reviews based on general-purpose development tools: Online software repositories such as Subversion, Mercurial, or Git and ticket systems such as Redmine or Trac allow groups of individuals to collaboratively review code. But there are also special purpose tools for collaborative code review that can facilitate the code review process.</p>
      <p>Static code analysis software lessens the task of reviewing large chunks of code on the developer by systematically checking source code for known vulnerabilities and defect types. A 2012 study by VDC Research reports that 17.6% of the embedded software engineers surveyed currently use automated tools to support peer code review and 23.7% expect to use them within 2 years.[20] Tools that work in the IDE are especially useful as they provide direct feedback to developers.</p>
    </div>
)


export default About;

