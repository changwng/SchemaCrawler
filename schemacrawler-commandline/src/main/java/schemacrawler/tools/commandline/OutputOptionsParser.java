/*
========================================================================
SchemaCrawler
http://www.schemacrawler.com
Copyright (c) 2000-2019, Sualeh Fatehi <sualeh@hotmail.com>.
All rights reserved.
------------------------------------------------------------------------

SchemaCrawler is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

SchemaCrawler and the accompanying materials are made available under
the terms of the Eclipse Public License v1.0, GNU General Public License
v3 or GNU Lesser General Public License v3.

You may elect to redistribute this code under any of these licenses.

The Eclipse Public License is available at:
http://www.eclipse.org/legal/epl-v10.html

The GNU General Public License v3 and the GNU Lesser General Public
License v3 are available at:
http://www.gnu.org/licenses/

========================================================================
*/

package schemacrawler.tools.commandline;


import static sf.util.Utility.isBlank;
import static us.fatehi.commandlineparser.CommandLineUtility.newCommandLine;

import java.io.File;
import java.nio.file.Path;

import picocli.CommandLine;
import schemacrawler.schemacrawler.Config;
import schemacrawler.tools.options.OutputOptions;
import schemacrawler.tools.options.OutputOptionsBuilder;

/**
 * Parses the command-line.
 *
 * @author Sualeh Fatehi
 */
public final class OutputOptionsParser
  implements OptionsParser<OutputOptions>
{

  private final OutputOptionsBuilder outputOptionsBuilder;

  private final CommandLine commandLine;

  @CommandLine.Option(names = {
    "-o", "--output-file" }, description = "Outfile file path and name")
  private File outputFile = null;
  @CommandLine.Option(names = {
    "--output-format" }, description = "Outfile format")
  private String outputFormatValue = null;

  @CommandLine.Parameters
  private String[] remainder = new String[0];

  public OutputOptionsParser(final Config config)
  {
    commandLine = newCommandLine(this);
    outputOptionsBuilder = OutputOptionsBuilder.builder().fromConfig(config);
  }

  @Override
  public OutputOptions parse(final String[] args)
  {
    commandLine.parse(args);

    if (outputFile != null)
    {
      final Path outputFilePath = outputFile.toPath().toAbsolutePath();
      outputOptionsBuilder.withOutputFile(outputFilePath);
    }

    if (!isBlank(outputFormatValue))
    {
      outputOptionsBuilder.withOutputFormatValue(outputFormatValue);
    }

    return outputOptionsBuilder.toOptions();
  }

  @Override
  public String[] getRemainder()
  {
    return remainder;
  }

}
